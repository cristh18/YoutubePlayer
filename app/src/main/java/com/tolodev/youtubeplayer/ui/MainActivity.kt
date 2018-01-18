package com.tolodev.youtubeplayer.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.tolodev.youtubeplayer.R
import com.tolodev.youtubeplayer.databinding.ActivityMainBinding
import com.tolodev.youtubeplayer.listener.YouTubePlayBackEventListener
import com.tolodev.youtubeplayer.listener.YouTubePlayerStateChangeListener
import com.tolodev.youtubeplayer.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private var player: YouTubePlayer? = null
    private lateinit var playerStateChangeListener: YouTubePlayerStateChangeListener
    private lateinit var playBackEventListener: YouTubePlayBackEventListener

    private lateinit var mainViewModel: MainViewModel
    private lateinit var subscriptions: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        setupView()
    }

    private fun init() {
        subscriptions = CompositeDisposable()
        mainViewModel = MainViewModel()
        initListeners()
    }

    private fun initListeners() {
        playerStateChangeListener = YouTubePlayerStateChangeListener(this)
        playBackEventListener = YouTubePlayBackEventListener(this)
    }

    private fun setupView() {
        activityMainBinding.viewModel = mainViewModel
        activityMainBinding.youtubeView.initialize(getString(R.string.youtube_api_key), this)
    }

    private fun handleSubscriptions() {
        subscriptions.add(videoViewedDisposable())
    }

    private fun videoViewedDisposable(): Disposable {
        return mainViewModel.videoViewedObservable().filter({ t -> t }).subscribe(this::showLevelUp, { it.printStackTrace() })
    }

    private fun showLevelUp(t: Boolean) {
        Toast.makeText(this, "Notify change", Toast.LENGTH_LONG).show()
        mainViewModel.videoViewed.set(t)
    }

    fun getVIdeoIdFromURL(videoUrl: String): Int {
        // TODO Implement fucntion for get Video ID from url
        //        player?.cueVideo("https://www.youtube.com/watch?v=jLuMq-ZAxqY")
        return 0
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        player = youTubePlayer
        player?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
        player?.setPlayerStateChangeListener(playerStateChangeListener)
        player?.setPlaybackEventListener(playBackEventListener)

        if (!wasRestored) {
            player?.cueVideo(getString(R.string.youtube_video_id))
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }

    override fun onResume() {
        super.onResume()
        handleSubscriptions()
    }
}
