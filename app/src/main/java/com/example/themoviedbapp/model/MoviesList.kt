package com.example.themoviedbapp.model

import com.google.gson.annotations.Expose

data class MoviesList(
    @Expose val page: Int,
    @Expose val results: List<MoviesListResult>,
    @Expose val total_pages: Int,
    @Expose val total_results: Int
)