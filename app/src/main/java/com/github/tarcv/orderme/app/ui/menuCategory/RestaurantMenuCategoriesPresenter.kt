package com.github.tarcv.orderme.app.ui.menuCategory

import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Place
import com.github.tarcv.orderme.core.data.response.GetMenuResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RestaurantMenuCategoriesPresenter(val place: Place) {

    @Inject
    lateinit var apiClient: ApiClient

    private var view: RestaurantMenuCategoriesView? = null
    private var isInitialized = false

    private lateinit var categories: List<Category>

    init {
        App.component.injectRestaurantMenuCategoriesPresenter(this)
        getCategories()
    }

    fun bind(view: RestaurantMenuCategoriesView) {
        this.view = view
        if (isInitialized)
            view.setCategories(categories)
    }

    fun unbind() {
        this.view = null
    }

    private fun setCategories() {
        if (view != null)
            view!!.setCategories(categories)
    }

    private fun getCategories() {
        IdlingResourceHelper.CountingIdlingResource.increment()
        apiClient.getMenu(place.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext,
                        this::onError)
    }

    private fun onNext(t: GetMenuResponse) {
        Timber.i("onNext: categories count = ${t.categories.size}")
        categories = t.categories
        isInitialized = true
        IdlingResourceHelper.CountingIdlingResource.decrement()
        setCategories()
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        IdlingResourceHelper.CountingIdlingResource.decrement()
        throwable.printStackTrace()
    }
}
