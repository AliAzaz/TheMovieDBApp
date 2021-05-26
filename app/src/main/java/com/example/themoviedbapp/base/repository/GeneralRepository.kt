package com.example.themoviedbapp.base.repository

import com.example.themoviedbapp.di.auth.AuthApi
import com.example.themoviedbapp.model.MoviesList
import com.example.themoviedbapp.model.MoviesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class GeneralRepository @Inject constructor(private val apiService: AuthApi) : GeneralDataSource {

    override suspend fun getSpecificMovie(movie_id: Int): MoviesResult {
        return apiService.getMovieData(
            movie_id = movie_id
        )
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

/*    override suspend fun getAllMovies(moviesList: MoviesList): Flow<MoviesModel> {
        return flow {
            val movie = MoviesModel(moviesList.page, mutableListOf<MovieResult>(),moviesList.total_pages)
            moviesList.results.forEach {

            }
            emit(

            )
        }
    }*/

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