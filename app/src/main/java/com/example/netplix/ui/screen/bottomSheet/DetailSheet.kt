package com.example.netplix.ui.screen.bottomSheet

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.netplix.ui.component.VideoPlayerComposable
import com.example.netplix.ui.screen.utility.IBottomScreen
import com.example.netplix.utils.getYoutubeLink
import com.example.netplix.utils.safe
import com.example.netplix.viewmodel.DetailViewModel
import com.google.android.exoplayer2.ExoPlayer

object DetailSheet : IBottomScreen {
    override val routeName: String
        get() = "detail"

    @Composable
    override fun Screen(navController: NavController, args: Bundle?) {
        
        val detailViewModel = hiltViewModel<DetailViewModel>()
        val context = LocalContext.current
        val exoPlayer = remember { ExoPlayer.Builder(context).build() }
        val isPlaying = remember {mutableStateOf(true)}

        LaunchedEffect(true) {
            detailViewModel.id = navController.previousBackStackEntry?.savedStateHandle
                ?.get<Int>("id") ?: 0
            Log.d("ID", detailViewModel.id.toString())
            detailViewModel.getMovie()
        }

        Column {
            Text(text = detailViewModel.details.value?.title.safe())
            VideoPlayerComposable(exoPlayer = exoPlayer, isPlaying = isPlaying, videoUrl = "https://www.youtube.com/watch?v=eIuBMM8FH1Q")
            if (detailViewModel.youtubeLink.value.isNotEmpty()) {
                Log.d("YOUTUBE", detailViewModel.youtubeLink.value)
            } else {
                CircularProgressIndicator()
            }
        }
    }

}