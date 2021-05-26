package com.example.themoviedbapp.base.viewmodel.usecases

import com.example.themoviedbapp.base.repository.GeneralDataSource
import com.example.themoviedbapp.model.MoviesModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class MovieUseCase @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        movie_id: Int
    ) = repository.getSpecificMovie(
        movie_id = movie_id
    )
}