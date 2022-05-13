package com.example.themoviedbapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesResult(
    @Expose val adult: Boolean,

    @SerializedName("backdrop_path")
    @Expose val backdrop_path: String,

    @Expose val belongs_to_collection: Any,
    @Expose val budget: Int,
    @Expose val genres: List<Genre>,
    @Expose val homepage: String,
    @Expose val id: Int,
    @Expose val imdb_id: String,
    @Expose val original_language: String,
    @Expose val original_title: String,
    @Expose val overview: String,
    @Expose val popularity: Double,

    @SerializedName("poster_path")
    @Expose val poster_path: String?,

    @Expose val production_companies: List<ProductionCompany>,
    @Expose val production_countries: List<ProductionCountry>,
    @Expose val release_date: String,
    @Expose val revenue: Int,
    @Expose val runtime: Int,
    @Expose val spoken_languages: List<SpokenLanguage>,
    @Expose val status: String,
    @Expose val tagline: String,
    @Expose val title: String,
    @Expose val video: Boolean,
    @Expose val vote_average: Double,
    @Expose val vote_count: Int
)