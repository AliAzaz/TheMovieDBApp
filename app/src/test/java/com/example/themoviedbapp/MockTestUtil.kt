package com.example.themoviedbapp

import com.example.themoviedbapp.model.*

/**
 * @author AliAzazAlam on 5/26/2021.
 */
class MockTestUtil {
    companion object {
        fun createZeroMovies(): MoviesList {
            return MoviesList(
                page = 1,
                results = listOf(),
                total_pages = 1,
                total_results = 0
            )
        }

        fun createMovies(): MoviesList {
            return MoviesList(
                page = 1,
                results = listOf(
                    MoviesListResult(
                        id = 457431,
                        adult = false
                    )
                ),
                total_pages = 1,
                total_results = 1
            )
        }

        fun createSingleMovieResponse() = createMovieResponse()[0]

         fun createMovieResponse() = listOf(
            MoviesResult(
                adult = false,
                backdrop_path = "/4eQmYf0skC3b8GGAzLHZnb0loR8.jpg",
                belongs_to_collection = Any(),
                budget = 0,
                genres = createGenreList(),
                homepage = "",
                id = 457431,
                imdb_id = "tt3000430",
                original_language = "fr",
                original_title = "Little Gay Boy",
                overview = "A young gay boy, from his birth to his teenage years, in which he experiments his sexuality and his own boundaries, to the day he finally meets his father.  In three acts= Act I — L'Annonciation or The Conception of a Little Gay Boy (2011); Act II — Little Gay Boy, ChrisT is Dead (2012); and Act III — Holy Thursday (The Last Supper) (2013).",
                popularity = 2.334,
                poster_path = "/1TRMDIBI966R39fzhdI733y2m1i.jpg",
                production_companies = createProductionCompanyList(),
                production_countries = createProductionCountriesList(),
                release_date = "2013-02-13",
                revenue = 0,
                runtime = 72,
                spoken_languages = createSpokenLanguageList(),
                status = "Released",
                tagline = "A Triptych",
                title = "Little Gay Boy",
                video = false,
                vote_average = 4.5,
                vote_count = 2
            )
        )

        private fun createGenreList(): List<Genre> {
            return listOf(
                Genre(
                    id = 18,
                    name = "Drama"
                )
            )
        }

        private fun createProductionCountriesList(): List<ProductionCountry> {
            return listOf(
                ProductionCountry(
                    iso_3166_1 = "FR",
                    name = "France"
                )
            )
        }

        private fun createProductionCompanyList(): List<ProductionCompany> {
            return listOf(
                ProductionCompany(
                    id = 13133,
                    logo_path = "",
                    name = "Universal Film Manufacturing Company",
                    origin_country = "US"
                )
            )
        }

        private fun createSpokenLanguageList(): List<SpokenLanguage> {
            return listOf(
                SpokenLanguage(
                    english_name = "English",
                    iso_639_1 = "en",
                    name = "English"
                ),
                SpokenLanguage(
                    english_name = "French",
                    iso_639_1 = "fr",
                    name = "Français"
                )
            )
        }
    }
}
