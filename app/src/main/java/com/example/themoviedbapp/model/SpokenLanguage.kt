package com.example.themoviedbapp.model

import com.google.gson.annotations.Expose

data class SpokenLanguage(
    @Expose val english_name: String,
    @Expose val iso_639_1: String,
    @Expose val name: String
)