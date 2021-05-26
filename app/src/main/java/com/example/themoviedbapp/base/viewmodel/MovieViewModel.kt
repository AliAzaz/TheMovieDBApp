package com.example.themoviedbapp.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedbapp.base.repository.ResponseStatusCallbacks
import com.example.themoviedbapp.base.viewmodel.usecases.MovieSearchUseCase
import com.example.themoviedbapp.base.viewmodel.usecases.MovieListUseCase
import com.example.themoviedbapp.base.viewmodel.usecases.MovieUseCase
import com.example.themoviedbapp.model.FetchDataModel
import com.example.themoviedbapp.model.MoviesModel
import com.example.themoviedbapp.model.MoviesResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class MovieViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val movieSearchUseCase: MovieSearchUseCase,
    private val movieUseCase: MovieUseCase,
) : ViewModel() {

    private val _moviesList: MutableLiveData<ResponseStatusCallbacks<FetchDataModel>> =
        MutableLiveData()

    val moviesResponse: MutableLiveData<ResponseStatusCallbacks<FetchDataModel>>
        get() = _moviesList

    private val _selectedMovies: MutableLiveData<ResponseStatusCallbacks<MoviesResult>> =
        MutableLiveData()

    val selectedMoviesResponse: MutableLiveData<ResponseStatusCallbacks<MoviesResult>>
        get() = _selectedMovies

    private var pagination = 1
    private var searchMovie = StringUtils.EMPTY
    private var startDate = StringUtils.EMPTY
    private var endDate = StringUtils.EMPTY
    private var updatedItems = arrayListOf<MoviesResult>()

    private var movieModel = MoviesModel(0, mutableListOf(), 0)
    private var min = 0
    private var max = 29

    init {
        fetchMoviesFromRemoteServer(pagination)
    }

    /*
    * Observed function for initiate searching
    * */
    fun fetchMoviesFromRemoteServer(pagination: Int) {
        _moviesList.value = ResponseStatusCallbacks.loading(
            data = FetchDataModel(
                page = pagination,
                moviesInfo = null
            )
        )
        viewModelScope.launch {
            try {
                movieListUseCase.invoke(page = pagination).collect { dataset ->

                    /*
                    * Getting for further call
                    * */
                    movieModel = MoviesModel(dataset.page, dataset.results, dataset.total_pages)

                    dataset.results.let {
                        if (it.isNotEmpty()) {
                            if (pagination == 1) {
                                updatedItems = arrayListOf()
                            }
                            max = if (dataset.results.size < max) dataset.results.size else max
                            (min..max).forEach { item ->
                                updatedItems.add(
                                    movieUseCase.invoke(it[item].id)
                                )
                            }
                            _moviesList.value = ResponseStatusCallbacks.success(
                                data = FetchDataModel(page = pagination, moviesInfo = updatedItems),
                                "Movies received"
                            )
                        } else
                            _moviesList.value = ResponseStatusCallbacks.error(
                                data = FetchDataModel(
                                    page = pagination,
                                    moviesInfo = null
                                ),
                                if (pagination == 1) "Sorry no movies received" else "Sorry no more movies available"
                            )
                    }


                }
            } catch (e: Exception) {
                _moviesList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

    /*
    * Observed function for loading more movies
    * */
    private fun loadMoreMoviesFromRemoteServer() {
        _moviesList.value = ResponseStatusCallbacks.loading(
            data = FetchDataModel(
                page = 0,
                moviesInfo = null
            )
        )
        viewModelScope.launch {
            try {
                movieModel.results.let { dataset ->
                    if (dataset.isNotEmpty()) {
                        max = if (dataset.size < max) dataset.size else max
                        (min..max).forEach { item ->
                            updatedItems.add(
                                movieUseCase.invoke(dataset[item].id)
                            )
                        }
                        _moviesList.value = ResponseStatusCallbacks.success(
                            data = FetchDataModel(page = pagination, moviesInfo = updatedItems),
                            "Movies received"
                        )
                    } else
                        _moviesList.value = ResponseStatusCallbacks.error(
                            data = FetchDataModel(
                                page = pagination,
                                moviesInfo = null
                            ),
                            if (pagination == 1) "Sorry no movies received" else "Sorry no more movies available"
                        )
                }

            } catch (e: Exception) {
                _moviesList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

    /*
    * Send data to detail page
    * */
    fun setSelectedProduct(singleMovies: MoviesResult) {
        _selectedMovies.value = ResponseStatusCallbacks.loading(null)
        viewModelScope.launch {
            try {
                _selectedMovies.value = ResponseStatusCallbacks.success(
                    data = singleMovies,
                    "Movie received"
                )
            } catch (e: Exception) {
                _moviesList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

    /*
    * load next page
    * */
    fun loadNextPagePhotos() {
        if (movieModel.results.size == max && pagination < movieModel.total_pages) {
            pagination++
            min = 0
            max = 29
            if (searchMovie == StringUtils.EMPTY) {
                fetchMoviesFromRemoteServer(pagination)
            } else {
                fetchSearchMoviesFromRemoteServer(pagination, endDate, startDate)
            }
        } else {
            min = max + 1
            max = max.times(2) + 1

            loadMoreMoviesFromRemoteServer()
        }

    }

    /*
    * Retry connection if internet is not available
    * */
    fun retryConnection() {
        if (min == 0 && max == 29) {
            if (searchMovie == StringUtils.EMPTY) {
                fetchMoviesFromRemoteServer(pagination)
            } else {
                fetchSearchMoviesFromRemoteServer(pagination, endDate, startDate)
            }
        } else {
            loadMoreMoviesFromRemoteServer()
        }
    }

    /*
    * Search function for searching photos by name
    * */
    fun searchMoviesFromRemote(endDate: String, startDate: String) {
        pagination = 1
        min = 0
        max = 29
        searchMovie = "$startDate -> $endDate"
        this.startDate = startDate
        this.endDate = endDate
        fetchSearchMoviesFromRemoteServer(pagination, endDate, startDate)
    }


    /*
    * Query to fetch movies from server
    * */
    private fun fetchSearchMoviesFromRemoteServer(
        pagination: Int,
        endDate: String,
        startDate: String
    ) {
        _moviesList.value = ResponseStatusCallbacks.loading(
            data = FetchDataModel(
                page = pagination,
                moviesInfo = null
            )
        )
        viewModelScope.launch {
            try {
                movieSearchUseCase(
                    page = pagination,
                    end_date = endDate,
                    start_date = startDate
                ).collect { dataset ->

                    /*
                    * Getting for further call
                    * */
                    movieModel = MoviesModel(dataset.page, dataset.results, dataset.total_pages)

                    dataset.results.let {
                        if (it.isNotEmpty()) {
                            if (pagination == 1) {
                                updatedItems = arrayListOf()
                            }
                            max = if (dataset.results.size < max) dataset.results.size else max
                            (min..max).forEach { item ->
                                updatedItems.add(
                                    movieUseCase.invoke(it[item].id)
                                )
                            }
                            _moviesList.value = ResponseStatusCallbacks.success(
                                data = FetchDataModel(page = pagination, moviesInfo = updatedItems),
                                "Movies received"
                            )
                        } else
                            _moviesList.value = ResponseStatusCallbacks.error(
                                data = FetchDataModel(
                                    page = pagination,
                                    moviesInfo = null
                                ),
                                if (pagination == 1) "Sorry no movies received" else "Sorry no more movies available"
                            )
                    }

                }
            } catch (e: Exception) {
                _moviesList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

}