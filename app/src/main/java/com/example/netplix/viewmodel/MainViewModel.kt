package com.example.netplix.viewmodel

import androidx.lifecycle.ViewModel
import com.example.netplix.data.api.ApiUrl
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    fun getImageUrl(filepath: String) : String =  filepath

}