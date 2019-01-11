package com.github.tarcv.orderme.app.ui.menu

import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Bucket
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Dish
import com.github.tarcv.orderme.core.data.response.GetMenuResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RestaurantMenuPresenter(private val category: Category)
    : RestaurantMenuFragment.OnCountButtonClickListener {
    override fun onMinusButtonCLicked(dish: Dish) {
        if (bucket.dishes[dish] != null) {
            bucket.dishes[dish] = bucket.dishes[dish]!! - 1
            if (bucket.dishes[dish]!! < 0)
                bucket.dishes[dish] = 0
        }
        view!!.refreshSum()
    }

    override fun onPlusButtonCLicked(dish: Dish) {
        if (bucket.dishes[dish] == null) {
            bucket.dishes[dish] = 1
        } else {
            bucket.dishes[dish] = bucket.dishes[dish]!! + 1
        }
        view!!.refreshSum()
    }

    @Inject
    lateinit var apiClient: ApiClient
    @Inject
    lateinit var bucket: Bucket

    var view: RestaurantMenuView? = null

    init {
        App.component.injectRestaurantMenuPresenter(this)
        getDishes()
    }

    fun bind(view: RestaurantMenuView) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }

    private fun setDishes(dishes: List<Dish>) {
        val dishesOfCategory = dishes.filter { it.categoryId == category.id }
        view?.setDishes(dishesOfCategory)
    }

    private fun getDishes() {
        val observable = apiClient.getMenu(category.placeId)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDishes,
                        this::onError)
    }

    private fun updateDishes(t: GetMenuResponse) {
        val dishesOfCategory = t.dishes.filter { it.categoryId == category.id }
        view?.setDishes(dishesOfCategory)
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        throwable.printStackTrace()
    }
}
