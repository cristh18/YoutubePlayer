package com.tolodev.youtubeplayer.manager

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class YoutubeControlManager {

    companion object {

        lateinit var youTubeControlInstance: YoutubeControlManager

        fun getInstance(): YoutubeControlManager = youTubeControlInstance

        fun initYoutubeControlManager() {
            youTubeControlInstance = YoutubeControlManager()
        }
    }

    private val videoStoppedSubject: PublishSubject<Boolean> = PublishSubject.create()

    private val videoEndedSubject: PublishSubject<Boolean> = PublishSubject.create()

    fun videoStoppedObservable(): Observable<Boolean> {
        return videoStoppedSubject.hide().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun videoEndedObservable(): Observable<Boolean> {
        return videoEndedSubject.hide().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun videoStoppedSubject(isStopped: Boolean) {
        videoStoppedSubject.onNext(isStopped)
    }

    fun videoEndedSubject(isStopped: Boolean) {
        videoEndedSubject.onNext(isStopped)
    }
}