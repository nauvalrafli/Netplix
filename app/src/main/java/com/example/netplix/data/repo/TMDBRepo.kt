package com.example.netplix.data.repo

import android.util.Log
import com.example.netplix.data.model.Movie
import com.example.netplix.data.model.MovieDetails
import com.example.netplix.data.model.Trailer
import com.example.netplix.utils.onResponseResult
import com.example.netplix.utils.safe
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class TMDBRepo @Inject constructor(
    private val baseRepo: BaseRepo
) {

    suspend fun discoverMovie(
        onSuccess: (List<Movie>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            baseRepo.provideApi().discoverMovie(baseRepo.apiKey)
                .onResponseResult(
                    {
                        onSuccess(it.results ?: listOf())
                        Log.d("SUCCESS", it.toString())
                    },
                    onError
                )
        } catch (e: Exception) {
            onError(e.message.safe())
        }
    }

    suspend fun getTopRated(
        onSuccess: (List<Movie>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            baseRepo.provideApi().getTopRated(baseRepo.apiKey)
                .onResponseResult(
                    { onSuccess(it.results ?: listOf()) },
                    onError
                )
        } catch (e: Exception) {
            onError(e.message.safe())
        }
    }

    suspend fun getPopular(
        onSuccess: (List<Movie>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            baseRepo.provideApi().getPopular(baseRepo.apiKey)
                .onResponseResult(
                    { onSuccess(it.results ?: listOf()) },
                    onError
                )
        } catch (e: Exception) {
            onError(e.message.safe())
        }
    }

    suspend fun getDetails(
        id: Int,
        onSuccess: (MovieDetails) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            baseRepo.provideApi().getDetails(id, baseRepo.apiKey)
                .onResponseResult(
                    onSuccess,
                    onError
                )
        } catch (e: Exception) {
            onError(e.message.safe())
        }
    }

    suspend fun searchMovie(
        query : String = "",
        page: Int = 1,
        onSuccess: (List<Movie>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            baseRepo.provideApi().searchMovie(baseRepo.apiKey, query, page)
                .onResponseResult(
                    { onSuccess(it.results ?: listOf()) },
                    onError
                )
        } catch (e : Exception) {
            Log.d("error", e.message.safe())
        }
    }

    suspend fun getTrailer(
        onSuccess: (List<Trailer>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            baseRepo.provideApi().getTrailer(baseRepo.apiKey)
                .onResponseResult(
                    { onSuccess(it.results ?: listOf()) },
                    onError
                )
        } catch (e : Exception) {
            Log.d("error", e.message.safe())
        }
    }

}