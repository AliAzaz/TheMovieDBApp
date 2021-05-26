package com.example.themoviedbapp.model

import kotlinx.coroutines.flow.Flow

/**
 * @author AliAzazAlam on 5/21/2021.
 */
data class MoviesModel(
    val page: Int,
    val results: List<MoviesListResult>,
    val total_pages: Int,
)