package com.example.themoviedbapp.base.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.themoviedbapp.MainCoroutinesRule
import com.example.themoviedbapp.MockTestUtil
import com.example.themoviedbapp.base.repository.ResponseStatusCallbacks
import com.example.themoviedbapp.base.viewmodel.usecases.MovieSearchUseCase
import com.example.themoviedbapp.base.viewmodel.usecases.MovieListUseCase
import com.example.themoviedbapp.base.viewmodel.usecases.MovieUseCase
import com.example.themoviedbapp.model.FetchDataModel
import com.example.themoviedbapp.model.MoviesResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @author AliAzazAlam on 5/26/2021.
 */
@RunWith(JUnit4::class)
class MovieViewModelTest {

    // Subject under test
    private lateinit var viewModel: MovieViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var movieListUseCase: MovieListUseCase

    @MockK
    lateinit var movieSearchUseCase: MovieSearchUseCase

    @MockK
    lateinit var movieUseCase: MovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getAllMoviesFromRemoteServer() returns list of movies`() = runBlocking {
        // Given
        val moviesListObserver =
            mockk<Observer<ResponseStatusCallbacks<FetchDataModel>>>(relaxed = true)

        // When
        coEvery {
            val items = arrayListOf<MoviesResult>()
            movieListUseCase.invoke(any())
                .collect {
                    it.results.forEach { item ->
                        items.add(
                            movieUseCase.invoke(item.id)
                        )
                    }
                }
            items.toList()
        }
            .returns(listOf(MockTestUtil.createSingleMovieResponse()))

        // Invoke
        viewModel = MovieViewModel(movieListUseCase, movieSearchUseCase, movieUseCase)
        viewModel.moviesResponse.observeForever(moviesListObserver)

        // Then
        coVerify(exactly = 1) {
            movieListUseCase.invoke()
        }
//        verify { moviesListObserver.onChanged(match { it.data != null }) }
//        verify { moviesListObserver.onChanged(match { it.data?.moviesInfo?.size == 1 }) }

    }

    @Test
    fun `test getAllMoviesFromRemoteServer() returns zero list of movies`() = runBlocking {
        // Given
        val moviesListObserver =
            mockk<Observer<ResponseStatusCallbacks<FetchDataModel>>>(relaxed = true)

        // When
        coEvery { movieListUseCase.invoke(any()) }
            .returns(
                flowOf(
                    MockTestUtil.createZeroMovies()
                )
            )

        // Invoke
        viewModel = MovieViewModel(movieListUseCase, movieSearchUseCase, movieUseCase)
        viewModel.moviesResponse.observeForever(moviesListObserver)

        // Then
        coVerify(exactly = 1) { movieListUseCase.invoke() }
        verify { moviesListObserver.onChanged(match { it.data != null }) }
        verify {
            moviesListObserver.onChanged(match {
                it.data?.moviesInfo?.isNullOrEmpty() ?: true
            })
        }

    }

    @After
    fun tearDown() {
    }
}