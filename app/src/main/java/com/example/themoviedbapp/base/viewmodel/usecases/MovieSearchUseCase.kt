package com.example.themoviedbapp.base.viewmodel.usecases

import com.example.themoviedbapp.base.repository.GeneralDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class MovieSearchUseCase @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        page: Int = 1,
        end_date: String,
        start_date: String
    ) = repository.getSearchMoviesList(
            page = page,
            end_date = end_date,
            start_date = start_date
        ).map {
            it.copy(
                results = it.results.filter { item -> item.adult != null && !item.adult }
            )
        }

}