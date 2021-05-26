package com.example.themoviedbapp.base.viewmodel.usecases

import com.example.themoviedbapp.MockTestUtil
import com.example.themoviedbapp.base.repository.GeneralRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @author AliAzazAlam on 5/26/2021.
 */
@RunWith(JUnit4::class)
class MovieSearchUseCaseTest {

    @MockK
    private lateinit var repository: GeneralRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getAllSearchMoviesByDate gives list of movies IDs`() = runBlocking {
        // Given
        val usecase = MovieSearchUseCase(repository)
        val mockMovies = MockTestUtil.createMovies()

        // When
        coEvery { repository.getSearchMoviesList(any(), any(), any()) }
            .returns(
                flowOf(mockMovies)
                    .map {
                        it.copy(
                            results = it.results.filter { item -> item.adult != null && item.adult == false }
                        )
                    }
            )

        // Invoke
        val movieListFlow = usecase(1,"22-05-2021","09-05-2021")

        // Then
        MatcherAssert.assertThat(movieListFlow, CoreMatchers.notNullValue())

        val movieListDataState = movieListFlow.first()
        MatcherAssert.assertThat(movieListDataState, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(movieListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            movieListDataState.results.size,
            CoreMatchers.`is`(mockMovies.results.size)
        )
    }


    @After
    fun tearDown() {
    }
}