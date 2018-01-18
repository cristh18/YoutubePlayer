//package com.tolodev.youtubeplayer.util
//
//import android.databinding.ObservableField
//import io.reactivex.Observable
//import io.reactivex.disposables.Disposable
//import java.util.*
//
//class ReadOnlyField<T>(source: Observable<T>) : ObservableField<T>() {
//
//    //protected constructor(source: Observable<T>)
//
//    val source: Observable<T>
//
//    val subscriptions = HashMap<android.databinding.Observable.OnPropertyChangedCallback, Disposable>()
//
//    init {
//        this.source = source.doOnNext(this::set).share()
//    }
//
//    companion object {
//        fun create(source: Observable<*>): ReadOnlyField<*> {
//            return ReadOnlyField(source)
//        }
//
//        fun createBoolean(source: Observable<Boolean>): ReadOnlyField<Boolean> {
//            return ReadOnlyField(source)
//        }
//    }
//
//    override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
//        super.addOnPropertyChangedCallback(callback)
//        subscriptions.put(callback, source.subscribe())
//    }
//
//
//    override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
//        super.removeOnPropertyChangedCallback(callback)
//        val subscription = subscriptions.remove(callback)
//        if (subscription != null && !subscription.isDisposed) {
//            subscription.dispose()
//        }
//    }
//
//
//}