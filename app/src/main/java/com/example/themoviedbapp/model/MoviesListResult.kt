package com.example.themoviedbapp.model

import com.google.gson.annotations.Expose

data class MoviesListResult(
    @Expose val adult: Boolean?,
    @Expose val id: Int
)