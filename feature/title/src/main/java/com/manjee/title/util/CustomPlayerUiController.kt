package com.manjee.title.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.manjee.nocaptionyoutubeplayer.PlayerConstants.PlayerState
import com.manjee.nocaptionyoutubeplayer.YouTubePlayer
import com.manjee.nocaptionyoutubeplayer.listeners.AbstractYouTubePlayerListener
import com.manjee.nocaptionyoutubeplayer.views.YouTubePlayerView
import com.manjee.title.R

class CustomPlayerUiController(
    private var context: Context,
    customPlayerUi: View?,
    youTubePlayer: YouTubePlayer,
    youTubePlayerView: YouTubePlayerView?
) : AbstractYouTubePlayerListener() {

    private var panel: View? = null

    fun initViews(playerUi: View) {
        panel = playerUi.findViewById(R.id.panel)
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {}

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {
        if (state == PlayerState.PLAYING || state == PlayerState.PAUSED || state == PlayerState.VIDEO_CUED) panel!!.setBackgroundColor(
            ContextCompat.getColor(context, android.R.color.transparent)
        ) else if (state == PlayerState.BUFFERING) panel!!.setBackgroundColor(
            ContextCompat.getColor(
                context,
                android.R.color.transparent
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
    }

    @SuppressLint("SetTextI18n")
    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
    }
}