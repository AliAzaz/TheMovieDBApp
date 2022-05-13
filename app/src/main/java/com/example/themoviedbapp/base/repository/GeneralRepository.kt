package com.example.themoviedbapp.base.repository

import com.example.themoviedbapp.di.auth.AuthApi
import com.example.themoviedbapp.di.auth.remote.ApiResponse
import com.example.themoviedbapp.di.auth.remote.message
import com.example.themoviedbapp.model.MoviesList
import com.example.themoviedbapp.model.MoviesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class GeneralRepository @Inject constructor(private val apiService: AuthApi) : GeneralDataSource {

    override suspend fun getSpecificMovie(movie_id: Int): ResultCallBack<MoviesResult> {
        var result: ResultCallBack<MoviesResult>
        apiService.getMovieData(movie_id = movie_id).apply {
            result = when (this) {
                is ApiResponse.ApiSuccessResponse -> {
                    data?.let {
                        ResultCallBack.Success(it)
                    } ?: ResultCallBack.Error("Null record")
                }
                is ApiResponse.ApiFailureResponse.Error -> {
                    ResultCallBack.Error(message())
                }
                is ApiResponse.ApiFailureResponse.Exception -> {
                    ResultCallBack.CallException(exception = this.exception as Exception)
                }
            }
        }
        return result
    }

    override suspend fun getAllMoviesList(page: Int): Flow<MoviesList> {
        return flow {
            emit(
                apiService.getMoviesList(
                    page = page
                )
            )
        }
    }

    override suspend fun getSearchMoviesList(
        page: Int,
        start_date: String,
        end_date: String
    ): Flow<MoviesList> {
        return flow {

            emit(
                apiService.getSearchMoviesList(
                    page = page,
                    end_date = end_date,
                    start_date = start_date
                )
            )
        }
    }

}