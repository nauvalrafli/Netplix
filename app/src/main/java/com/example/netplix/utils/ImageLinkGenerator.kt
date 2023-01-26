package com.example.netplix.utils

import com.example.netplix.data.api.ApiUrl

fun getImageLink(filePath: String) = ApiUrl.baseImageUrl + filePath

fun getYoutubeLink(key : String) = "https://www.youtube.com/watch?v=$key"