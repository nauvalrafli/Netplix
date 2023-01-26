package com.example.netplix.ui.component

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.netplix.MainActivity
import com.example.netplix.utils.safe
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

@Composable
fun VideoPlayerComposable(
    videoUrl: String? = "",
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    isPlaying: MutableState<Boolean>
) {
    AndroidView(
        factory = { context ->
            StyledPlayerView(context)
        },
        update = {
            it.apply {
                player = exoPlayer.apply {
                    clearMediaItems()
                    val mediaItem = MediaItem.Builder()
                        .setUri(Uri.parse(videoUrl.safe()))
                        .build()
                    setMediaItem(mediaItem)
                    playWhenReady = isPlaying.value
                    prepare()
                }
            }
        },
        modifier = modifier
    )
}