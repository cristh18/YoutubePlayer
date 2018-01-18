package com.tolodev.youtubeplayer.listener

import android.content.Context
import android.widget.Toast
import com.google.android.youtube.player.YouTubePlayer
import com.tolodev.youtubeplayer.manager.YoutubeControlManager


class YouTubePlayerStateChangeListener(private var context: Context) : YouTubePlayer.PlayerStateChangeListener {

    lateinit var videoViewedListener: VideoViewedListener

    override fun onLoading() {
        // Called when the player is loading a video
        // At this point, it's not ready to accept commands affecting playback such as play() or pause()
    }

    override fun onLoaded(s: String) {
        // Called when a video is done loading.
        // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
    }

    override fun onAdStarted() {
        // Called when playback of an advertisement starts.
    }

    override fun onVideoStarted() {
        Toast.makeText(context, "Video started", Toast.LENGTH_SHORT).show()
        YoutubeControlManager.getInstance().videoEndedSubject(false)
    }

    override fun onVideoEnded() {
        Toast.makeText(context, "Video ended", Toast.LENGTH_SHORT).show()
        YoutubeControlManager.getInstance().videoEndedSubject(true)

//        videoViewedListener.videoViewed()
    }

    override fun onError(errorReason: YouTubePlayer.ErrorReason) {
        // Called when an error occurs.
    }

    interface VideoViewedListener {
        fun videoViewed()
    }
}