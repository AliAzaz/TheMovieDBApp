package com.example.themoviedbapp.di.auth

import com.example.themoviedbapp.MainCoroutinesRule
import com.example.themoviedbapp.di.modules.NetworkApiModuleTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * @author AliAzazAlam on 5/26/2021.
 */
class AuthApiTest : NetworkApiModuleTest<AuthApi>() {

    private lateinit var apiService: AuthApi

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(AuthApi::class.java)
    }

    @After
    fun tearDown() {
    }

    @Throws(IOException::class)
    @Test
    fun `test getMovieData() returns single Movie`() = runBlocking {
        // Given
        enqueueResponse("/movie_response.json")

        // Invoke
        val response = apiService.getMovieData(457431)
        val responseBody = requireNotNull(response)
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody.id, CoreMatchers.`is`(457431))
        MatcherAssert.assertThat(
            responseBody.poster_path,
            CoreMatchers.`is`("/1TRMDIBI966R39fzhdI733y2m1i.jpg")
        )
        MatcherAssert.assertThat(responseBody.release_date, CoreMatchers.`is`("2013-02-13"))
        MatcherAssert.assertThat(responseBody.popularity, CoreMatchers.`is`(2.334))
        MatcherAssert.assertThat(
            responseBody.overview,
            CoreMatchers.`is`("A young gay boy, from his birth to his teenage years, in which he experiments his sexuality and his own boundaries, to the day he finally meets his father.  In three acts: Act I — L'Annonciation or The Conception of a Little Gay Boy (2011); Act II — Little Gay Boy, ChrisT is Dead (2012); and Act III — Holy Thursday (The Last Supper) (2013).")
        )
        MatcherAssert.assertThat(
            responseBody.spoken_languages[0].name,
            CoreMatchers.`is`("English")
        )
        MatcherAssert.assertThat(responseBody.genres[0].name, CoreMatchers.`is`("Drama"))
        MatcherAssert.assertThat(responseBody.adult, CoreMatchers.`is`(false))
        MatcherAssert.assertThat(
            responseBody.original_language,
            CoreMatchers.`is`("fr")
        )
    }

    @Throws(IOException::class)
    @Test
    fun `test getMovieList() returns list of Movies IDs`() = runBlocking {
        // Given
        enqueueResponse("/movie_list_response.json")

        // Invoke
        val response = apiService.getMoviesList()
        val responseBody = requireNotNull(response.results)
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody[0].adult, CoreMatchers.`is`(false))
        MatcherAssert.assertThat(
            responseBody[0].id,
            CoreMatchers.`is`(457431)
        )
    }

    @Throws(IOException::class)
    @Test
    fun `test getMovieListBySearch() returns list of Movies IDs`() = runBlocking {
        // Given
        enqueueResponse("/movie_list_response.json")

        // Invoke
        val response = apiService.getSearchMoviesList(
            end_date = "22-05-2021",
            start_date = "09-05-2021"
        )
        val responseBody = requireNotNull(response.results)
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody[0].adult, CoreMatchers.`is`(false))
        MatcherAssert.assertThat(
            responseBody[0].id,
            CoreMatchers.`is`(457431)
        )
    }
}