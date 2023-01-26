package com.example.netplix.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netplix.data.model.MovieDetails
import com.example.netplix.data.model.Trailer
import com.example.netplix.data.repo.TMDBRepo
import com.example.netplix.utils.getYoutubeLink
import com.example.netplix.utils.safe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tmdbRepo: TMDBRepo
) : ViewModel() {

    var id = 0
    var details = mutableStateOf<MovieDetails?>(null)
    var youtubeLink = mutableStateOf("")

    suspend fun getMovie() {
        tmdbRepo.getDetails(id, onSuccess = {
            details.value = it
            viewModelScope.launch {
                getTrailer()
            }
        }, {
            Log.d("MovieError", it)
        })
    }

    private suspend fun getTrailer() {
        tmdbRepo.getTrailer(onSuccess = {
            youtubeLink.value = getYoutubeLink(it[0].key.safe())
            Log.d("YOUTUBE", youtubeLink.value)
        }, {
            Log.d("MovieError", it)
        })
    }

}