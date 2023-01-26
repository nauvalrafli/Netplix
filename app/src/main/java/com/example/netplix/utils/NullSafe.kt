package com.example.netplix.utils

fun String?.safe() : String = this ?: ""
fun Int?.safe() : Int = this ?: 0