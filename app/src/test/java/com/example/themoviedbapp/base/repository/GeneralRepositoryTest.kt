package com.example.themoviedbapp.base.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.themoviedbapp.MainCoroutinesRule
import com.example.themoviedbapp.MockTestUtil
import com.example.themoviedbapp.di.auth.AuthApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
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
class GeneralRepositoryTest {

    // Subject under test
    private lateinit var repository: GeneralRepository

    @MockK
    private lateinit var authApi: AuthApi

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getAllMovies returns list of movies`() = runBlocking {
        // Given
        repository = GeneralRepository(authApi)

        // When
        coEvery { authApi.getMoviesList(any()) }.returns(MockTestUtil.createMovies())

        // Invoke
        val moviesFlow = repository.getAllMoviesList(1)

        // Then
        MatcherAssert.assertThat(moviesFlow, CoreMatchers.notNullValue())

        val movies = moviesFlow.first()
        MatcherAssert.assertThat(movies, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movies.results, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movies.results.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(movies.results[0].id, CoreMatchers.`is`(457431))
    }

    @Test
    fun `test getAllMovies returns zero list of movies`() = runBlocking {
        // Given
        repository = GeneralRepository(authApi)
        val moviesList = MockTestUtil.createZeroMovies()

        // When
        coEvery { authApi.getMoviesList(any()) }.returns(moviesList)

        // Invoke
        val moviesFlow = repository.getAllMoviesList(1)

        // Then
        MatcherAssert.assertThat(moviesFlow, CoreMatchers.notNullValue())

        val movies = moviesFlow.first()
        MatcherAssert.assertThat(movies, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movies.results, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            movies.results.size,
            CoreMatchers.`is`(moviesList.results.size)
        )
    }

    @Test
    fun `test searchMovie returns list of movies ID's`() = runBlocking {
        //Given
        repository = GeneralRepository(authApi)
        val searchMovie = MockTestUtil.createMovies()

        //When
        coEvery {
            authApi.getSearchMoviesList(any(), any(),any())
        }.returns(searchMovie)

        //Invoke
        val moviesFlow = repository.getSearchMoviesList(1, "22-05-2021","09-05-2021")

        //Then
        MatcherAssert.assertThat(moviesFlow, CoreMatchers.notNullValue())

        val movies = moviesFlow.first()
        MatcherAssert.assertThat(movies, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movies.results, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            movies.results.size,
            CoreMatchers.`is`(searchMovie.results.size)
        )

    }

    @After
    fun tearDown() {

    }
}