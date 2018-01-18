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

class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, YouTubePlayerStateChangeListener.VideoViewedListener {

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
    }

    private fun setupView() {
        activityMainBinding.viewModel = mainViewModel
        activityMainBinding.youtubeView.initialize(getString(R.string.youtube_api_key), this)
    }

    private fun handleSubscriptions() {
        subscriptions.add(videoViewedDisposable())
    }

    private fun videoViewedDisposable(): Disposable {

        return mainViewModel.videoViewedObservable().subscribe({ t ->
            run {
                Toast.makeText(this, "Notify change", Toast.LENGTH_LONG).show()
                mainViewModel.videoViewed.set(t)
            }
        }, { it.printStackTrace() })
    }

    fun getVIdeoIdFromURL(videoUrl: String): Int {
        // TODO Implement fucntion for get Video ID from url
        //        player?.cueVideo("https://www.youtube.com/watch?v=jLuMq-ZAxqY")
        return 0
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        player = youTubePlayer
        player?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)

        playerStateChangeListener = YouTubePlayerStateChangeListener(this)
        playerStateChangeListener.videoViewedListener = this

        playBackEventListener = YouTubePlayBackEventListener(this)




        player?.setPlayerStateChangeListener(playerStateChangeListener)
        player?.setPlaybackEventListener(playBackEventListener)

        if (!wasRestored) {
//            player?.cueVideo("jLuMq-ZAxqY")
            player?.cueVideo("668nUCeBHyY")
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }

    override fun onResume() {
        super.onResume()
        handleSubscriptions()
    }

    override fun videoViewed() {
        Toast.makeText(this, "Notify change", Toast.LENGTH_LONG).show()
        mainViewModel.videoViewed.set(true)
    }
}
