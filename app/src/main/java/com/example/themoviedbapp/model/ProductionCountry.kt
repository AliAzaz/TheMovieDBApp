package com.example.themoviedbapp.model

import com.google.gson.annotations.Expose

data class ProductionCountry(
    @Expose val iso_3166_1: String,
    @Expose val name: String
)