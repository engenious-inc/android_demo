package com.github.tarcv.orderme.app.ui.makeReservation

import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Utils
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Place
import com.github.tarcv.orderme.core.data.entity.Reserve
import com.github.tarcv.orderme.core.data.response.MakeReservationResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

class MakeReservationPresenter(val place: Place) {
    var view: MakeReservationView? = null
    var calendar = Calendar.getInstance()
    var created: String
    @Inject
    lateinit var apiClient: ApiClient

    init {
        App.component.inject(this)
        calendar.timeInMillis = System.currentTimeMillis()
        created = Utils.getFullDate(System.currentTimeMillis())
    }

    fun timeChanged(hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
    }

    fun dateChanged(year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

    fun bind(view: MakeReservationView) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }

    fun book() {
        if (App.sharedPreferences.getString(App.LOGIN_TOKEN, "") == "") {
            view?.notifyNotLoggedIn()
            return
        }

        val reserve = try {
            Reserve(
                place.id,
                Utils.getFullDate(calendar.timeInMillis),
                created,
                view!!.getPhoneNumber(),
                view!!.getPeopleCount()
            )
        } catch (e: Exception) {
            view?.notifyReservationIncomplete()
            return
        }

        apiClient.makeReservation(reserve)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext,
                        this::onError)

        Timber.i("book: $reserve")
    }

    private fun onNext(response: MakeReservationResponse) {
        Timber.i("onNext: $response")
        view?.notifyReservationMade()
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        throwable.printStackTrace()
    }
}