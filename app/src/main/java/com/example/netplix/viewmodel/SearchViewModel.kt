package com.example.netplix.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.netplix.data.api.TMDBApi
import com.example.netplix.data.model.Movie
import com.example.netplix.data.repo.TMDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val TMDBRepo : TMDBRepo
) : ViewModel() {

    val searchKeyword = mutableStateOf("")
    val searchResult = mutableStateListOf<Movie>()
    var currentPage = 1

    suspend fun searchMovie(
        page: Int = 1,
        onError: (String) -> Unit = {}
    ) {
        currentPage = 1
        TMDBRepo.searchMovie(
            query = searchKeyword.value,
            onSuccess = {
                if(it.isNotEmpty()) {
                    searchResult.clear()
                    searchResult.addAll(it)
                }
            },
            onError = onError
        )
    }

    suspend fun nextPage(onError: (String) -> Unit = {}) {
        TMDBRepo.searchMovie(
            query = searchKeyword.value,
            page = currentPage + 1,
            onSuccess = {
                if(it.isNotEmpty()) {
                    searchResult.addAll(it)
                }
            },
            onError = onError
        )
    }

}