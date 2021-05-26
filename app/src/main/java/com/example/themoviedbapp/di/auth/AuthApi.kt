package com.example.themoviedbapp.di.auth

import com.example.themoviedbapp.model.MoviesList
import com.example.themoviedbapp.model.MoviesResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author AliAzazAlam on 5/16/2021.
 */
interface AuthApi {

    @GET(ApiRoutes.GET_MOVIES_LIST)
    suspend fun getMoviesList(
        @Query("page") page: Int = 1,
    ): MoviesList

    @GET(ApiRoutes.GET_MOVIES_FIRST + "{movie_id}" + ApiRoutes.GET_MOVIES_SECOND)
    suspend fun getMovieData(
        @Path("movie_id") movie_id: Int,
    ): MoviesResult

    @GET(ApiRoutes.GET_MOVIES_LIST)
    suspend fun getSearchMoviesList(
        @Query("page") page: Int = 1,
        @Query("end_date") end_date: String,
        @Query("start_date") start_date: String
    ): MoviesList

}