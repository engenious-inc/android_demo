package com.github.tarcv.orderme.app

import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Place
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.Collections

class PlacesRepository constructor(
    client: ApiClient
) {
    val source: Observable<List<Place>> = client
            .getPlaces()
            .subscribeOn(Schedulers.io())
            .toObservable()
            .share()

    init {
        IdlingResourceHelper.countingIdlingResource.increment()
        source
                .observeOn(Schedulers.computation())
                .subscribe({
                    try {
                        setPlaces(it)
                    } finally {
                        IdlingResourceHelper.countingIdlingResource.decrement()
                    }
                }, { e ->
                    Timber.w(e, "Places source signalled error")
                    IdlingResourceHelper.countingIdlingResource.decrement()
                })
    }

    private val map = Collections.synchronizedMap(HashMap<Int, Place>())

    private fun setPlaces(places: List<Place>) {
        synchronized(map) {
            map.clear()
            places.forEach { map[it.id] = it }
        }
    }

    fun getNameById(id: Int): String {
        return map[id]?.name ?: "$id"
    }

    fun getPlaceById(id: Int): Place? {
        return map[id]
    }

    fun getPlacesByName(name: String): List<Place> {
        val result = ArrayList<Place>()
        synchronized(map) {
            for ((id, place) in map) {
                if (place.name.toLowerCase().contains(name.toLowerCase()))
                    result.add(place)
            }
        }
        return result
    }
}