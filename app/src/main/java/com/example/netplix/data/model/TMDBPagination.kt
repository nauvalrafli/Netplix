package com.example.netplix.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class TMDBPagination<T>(
    val page: Int?,
    val results: List<T>?,
    val total_pages: Int?,
    val total_result: Int?
)
