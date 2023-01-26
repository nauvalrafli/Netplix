package com.example.netplix.data.api

object ApiUrl {
    const val baseUrl = "https://api.themoviedb.org/3/"
    const val baseImageUrl = "https://image.tmdb.org/t/p/w500/"

    const val discoverMovie = "${baseUrl}discover/movie"

    object Movie {
        const val baseMovieUrl = "${baseUrl}movie/"
        const val movieDetail = "${baseMovieUrl}{id}"
        const val popular = "${baseMovieUrl}popular"
        const val topRated = "${baseMovieUrl}top_rated"
        const val getTrailer = "${baseMovieUrl}{movie_id}/videos"
    }

    object Search {
        const val searchMovie = "${baseUrl}search/movie"
    }
}