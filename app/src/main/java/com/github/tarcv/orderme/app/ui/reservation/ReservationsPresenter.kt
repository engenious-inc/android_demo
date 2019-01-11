package com.github.tarcv.orderme.app.ui.reservation

import android.support.annotation.MainThread
import android.util.Log
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Reserve
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class ReservationsPresenter(
    private val apiClient: ApiClient
) {
    private var view: ReservationView? = null

    private val reserveSubject = BehaviorSubject.create<List<Reserve>>()

    init {
        App.component.injectReservationPresenter(this)
        Timber.i("ctor")
    }

    @MainThread
    fun bind(view: ReservationView) {
        Timber.i("bind")
        this.view = view
        updateReservations()
        view.wireReservationsSource(reserveSubject)
    }

    @MainThread
    fun unbind() {
        Timber.i("unbind")
        this.view = null
    }

    fun updateReservations() {
        apiClient.getReservations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext,
                        this::onError)
    }

    private fun onNext(response: List<Reserve>) {
        Timber.i("onNext")
        Log.d("ReservationPresenter", "onNext")
        reserveSubject.onNext(response)
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        Log.d("ReservationPresenter", "onError")
        throwable.printStackTrace()
    }
}
