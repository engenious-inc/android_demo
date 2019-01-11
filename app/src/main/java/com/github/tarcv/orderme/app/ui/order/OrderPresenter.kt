package com.github.tarcv.orderme.app.ui.order

import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Order
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class OrderPresenter {

    @Inject
    lateinit var apiClient: ApiClient

    private var view: OrderView? = null
    private var isInitialized = false
    private lateinit var orders: List<Order>

    init {
        App.component.injectOrderPresenter(this)
        getOrders()
    }

    fun bind(view: OrderView) {
        this.view = view
        if (isInitialized)
            view.setOrders(orders)
    }

    fun unbind() {
        this.view = null
    }

    private fun setOrders() {
        if (view != null)
            view!!.setOrders(orders)
    }

    fun getOrders() {
        apiClient.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext,
                        this::onError)
    }

    private fun onNext(response: List<Order>) {
        Timber.i("onNext")
        orders = response
        isInitialized = true
        setOrders()
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        throwable.printStackTrace()
    }
}
