package com.example.themoviedbapp.base.viewmodel.usecases

import com.example.themoviedbapp.MockTestUtil
import com.example.themoviedbapp.base.repository.GeneralRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

/**
 * @author AliAzazAlam on 5/27/2021.
 */
@RunWith(JUnit4::class)
class MovieUseCaseTest : TestCase() {

    @MockK
    private lateinit var repository: GeneralRepository

    @Before
    public override fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getSpecificMovie by providing movieID`() = runBlocking {
        //Given
        val useCase = MovieUseCase(repository)
        val mockMovie = MockTestUtil.createSingleMovieResponse()

        //When
        coEvery {
            repository.getSpecificMovie(any())
        }.returns(
            mockMovie
        )

        //Invoke
        val movieResponse = useCase(457431)

        //Then
        MatcherAssert.assertThat(movieResponse, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(movieResponse.release_date, CoreMatchers.`is`("2013-02-13"))
        MatcherAssert.assertThat(movieResponse.popularity, CoreMatchers.`is`(2.334))
        MatcherAssert.assertThat(
            movieResponse.overview,
            CoreMatchers.`is`("A young gay boy, from his birth to his teenage years, in which he experiments his sexuality and his own boundaries, to the day he finally meets his father.  In three acts= Act I — L'Annonciation or The Conception of a Little Gay Boy (2011); Act II — Little Gay Boy, ChrisT is Dead (2012); and Act III — Holy Thursday (The Last Supper) (2013).")
        )
        MatcherAssert.assertThat(
            movieResponse.spoken_languages[0].name,
            CoreMatchers.`is`("English")
        )
        MatcherAssert.assertThat(movieResponse.genres[0].name, CoreMatchers.`is`("Drama"))
        MatcherAssert.assertThat(movieResponse.adult, CoreMatchers.`is`(false))
        MatcherAssert.assertThat(
            movieResponse.original_language,
            CoreMatchers.`is`("fr")
        )

    }


    @After
    public override fun tearDown() {
    }
}