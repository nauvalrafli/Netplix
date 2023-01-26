package com.example.netplix.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netplix.data.model.Movie
import com.example.netplix.data.model.MovieDetails
import com.example.netplix.data.repo.TMDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tmdbRepo: TMDBRepo
) : ViewModel() {

    init {
        viewModelScope.launch {
            getTopRated()
            getDiscovery()
            getPopular()
        }
    }

    val discoverMovie = mutableStateListOf<Movie>()
    val popularMovie = mutableStateListOf<Movie>()
    val topRatedMovie = mutableStateListOf<Movie>()

    val movieDetails = mutableStateOf<MovieDetails?>(null)

    suspend fun getDiscovery() {
        tmdbRepo.discoverMovie(
            onSuccess = {
                if(it.isNotEmpty()){
                    discoverMovie.clear()
                    discoverMovie.addAll(it)
                }
            },
            onError = {
                Log.d("ERRORMESSAGE", it)
            }
        )
    }

    suspend fun getPopular() {
        tmdbRepo.getPopular(
            onSuccess = {
                if (it.isNotEmpty()) {
                    popularMovie.clear()
                    popularMovie.addAll(it)
                }
            },
            onError = {
                Log.d("ERRORMESSAGE", it)
            }
        )
    }

    suspend fun getTopRated() {
        tmdbRepo.getTopRated(
            onSuccess = {
                if (it.isNotEmpty()) {
                    topRatedMovie.clear()
                    topRatedMovie.addAll(it)
                }
            },
            onError = {
                Log.d("ERRORMESSAGE", it)
            }
        )
    }

    suspend fun getDetail(id: Int) {
        tmdbRepo.getDetails(
            id,
            onSuccess = {
                movieDetails.value = it
            },
            onError = {
                Log.d("ERRORMESSAGE", it)
            }
        )
    }

}