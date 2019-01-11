package com.github.tarcv.orderme.app.ui.bucket

import android.support.design.widget.Snackbar
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.App.Companion.tryGetTable
import com.github.tarcv.orderme.app.Bucket
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Dish
import com.github.tarcv.orderme.core.data.request.MakeOrderRequest
import com.github.tarcv.orderme.core.data.response.MakeOrderResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class BucketPresenter(val placeId: Int) : BucketFragment.OnCountButtonClickListener {

    var view: BucketFragmentView? = null
    @Inject
    lateinit var apiClient: ApiClient
    @Inject
    lateinit var bucket: Bucket

    init {
        App.component.inject(this)
    }

    fun bind(view: BucketFragmentView) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }

    override fun onMinusButtonCLicked(dish: Dish) {
        if (bucket.dishes[dish] != null) {
            bucket.dishes[dish] = bucket.dishes[dish]!! - 1
            if (bucket.dishes[dish]!! < 0)
                bucket.dishes[dish] = 0
        }
        view!!.refreshTotal()
    }

    override fun onPlusButtonCLicked(dish: Dish) {
        if (bucket.dishes[dish] == null)
            bucket.dishes[dish] = 1
        else
            bucket.dishes[dish] = bucket.dishes[dish]!! + 1
        view!!.refreshTotal()
    }

    fun deleteAll() {
        bucket.dishes = mutableMapOf<Dish, Int>()
        view!!.refreshTotal()
        view!!.notifyDataSetChanged()
    }

    fun accept() {
        val table = tryGetTable()
        if (table == null || table.place != placeId) {
            notifyTableIdIsNull()
            return
        }
        if (App.sharedPreferences.getString(App.LOGIN_TOKEN, "") == "") {
            view?.notifyNotLoggedIn()
            return
        }
        var map = mutableMapOf<String, Int>()
        for ((dish, count) in bucket.dishes)
            map.put(dish.id.toString(), count)
        val request = MakeOrderRequest(placeId,
                table.tableNumber,
                map,
                view!!.getComment(),
                view!!.getDate(),
                view!!.getSum())

        apiClient.makeOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onError)
    }

    private fun onNext(response: MakeOrderResponse) {
        Timber.i("onNext")
        bucket.dishes.clear()
        view!!.onOrderAccepted()
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        throwable.printStackTrace()
        view!!.onOrderError()
    }

    private fun notifyTableIdIsNull() {

        Snackbar.make(view!!.getView(), "Detect your table, please", Snackbar.LENGTH_LONG)
                .setAction("Detect", {
                    view!!.detectTable()
                    Timber.i("Notify table id is null")
                }).show()
    }
}