package com.example.netplix.ui.screen.utility

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface IScreen {
    val routeName : String

    @Composable
    fun Screen(navController: NavController, args: Bundle?)

    @Composable
    fun BaseScreen(navController: NavController, args: Bundle?) {
        Screen(navController, args)
    }
}