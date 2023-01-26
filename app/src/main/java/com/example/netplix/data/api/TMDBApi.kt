package com.example.netplix.data.api

import com.example.netplix.data.model.Movie
import com.example.netplix.data.model.MovieDetails
import com.example.netplix.data.model.TMDBPagination
import com.example.netplix.data.model.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET(ApiUrl.discoverMovie)
    suspend fun discoverMovie(@Query("api_key") key: String, @Query("page") page: Int = 1) : Response<TMDBPagination<Movie>>

    @GET(ApiUrl.Movie.movieDetail)
    suspend fun getDetails(@Path("id") id : Int, @Query("api_key") key: String) : Response<MovieDetails>

    @GET(ApiUrl.Movie.popular)
    suspend fun getPopular(@Query("api_key") key: String, @Query("page") page: Int = 1) : Response<TMDBPagination<Movie>>

    @GET(ApiUrl.Movie.topRated)
    suspend fun getTopRated(@Query("api_key") key: String, @Query("page") page: Int = 1) : Response<TMDBPagination<Movie>>

    @GET(ApiUrl.Search.searchMovie)
    suspend fun searchMovie(@Query("api_key") key: String, @Query("query") searchKeyword: String, @Query("page") page: Int = 1) : Response<TMDBPagination<Movie>>

    @GET(ApiUrl.Movie.getTrailer)
    suspend fun getTrailer(@Query("api_key") key: String) : Response<TMDBPagination<Trailer>>
}