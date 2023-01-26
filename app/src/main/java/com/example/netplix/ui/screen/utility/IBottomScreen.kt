package com.example.netplix.ui.screen.utility

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface IBottomScreen {
    val routeName: String

    @Composable
    fun Screen(navController: NavController, args: Bundle?)
}