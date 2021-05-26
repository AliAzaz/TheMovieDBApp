package com.example.themoviedbapp.base.repository

import com.example.themoviedbapp.model.MoviesList
import com.example.themoviedbapp.model.MoviesResult
import kotlinx.coroutines.flow.Flow

/**
 * @author AliAzazAlam on 5/16/2021.
 */
interface GeneralDataSource {

    /*
    * Get movie from server
    * */
    suspend fun getSpecificMovie(
        movie_id: Int
    ): MoviesResult
    /*
    * Get movie from server End
    * */

    /*
    * Get all movies list from server
    * */
    suspend fun getAllMoviesList(
        page: Int
    ): Flow<MoviesList>
    /*
    * Get all movies list from server End
    * */

    /*
    * Get search movies list
    * */
    suspend fun getSearchMoviesList(
        page: Int,
        start_date: String,
        end_date: String
    ): Flow<MoviesList>
    /*
    * Get search movies list End
    * */


}