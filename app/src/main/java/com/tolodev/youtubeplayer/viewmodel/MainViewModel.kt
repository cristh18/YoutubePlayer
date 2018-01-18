package com.tolodev.youtubeplayer.viewmodel

import android.databinding.ObservableField
import com.tolodev.youtubeplayer.manager.YoutubeControlManager
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MainViewModel {

    var videoViewed: ObservableField<Boolean> = ObservableField(false)

    fun videoViewedObservable(): Observable<Boolean> {
        return Observable.combineLatest(
                YoutubeControlManager.getInstance().videoEndedObservable(),
                YoutubeControlManager.getInstance().videoStoppedObservable(),
                BiFunction { ended, stopped -> ended && stopped })
    }
}