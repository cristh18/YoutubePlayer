package com.tolodev.youtubeplayer.listener

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.youtube.player.YouTubePlayer
import com.tolodev.youtubeplayer.manager.YoutubeControlManager

class YouTubePlayBackEventListener(private val context: Context) : YouTubePlayer.PlaybackEventListener {

    override fun onSeekTo(p0: Int) {
        Toast.makeText(context, "Seeking video", Toast.LENGTH_SHORT).show()
    }

    override fun onBuffering(p0: Boolean) {
        Log.e(YouTubePlayBackEventListener::class.java.name, "Buffering video")
    }

    override fun onPlaying() {
        Toast.makeText(context, "Playing video", Toast.LENGTH_SHORT).show()
        YoutubeControlManager.getInstance().videoPlaying()
    }

    override fun onStopped() {
        Toast.makeText(context, "Video stopped", Toast.LENGTH_SHORT).show()
        YoutubeControlManager.getInstance().videoStopped()
    }

    override fun onPaused() {
        Toast.makeText(context, "Video paused", Toast.LENGTH_SHORT).show()
    }
}