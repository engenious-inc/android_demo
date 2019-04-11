package com.github.tarcv.orderme.app.ui.home

import android.support.annotation.MainThread
import com.github.tarcv.orderme.app.PlacesRepository
import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.core.data.entity.Place
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HomePresenter constructor(
    private val placesRepository: PlacesRepository,
    private val mainScheduler: Scheduler
) : PlacesProvider {

    var view: HomeView? = null

    @MainThread
    fun bind(view: HomeView, searchTextChanges: Observable<CharSequence>) {
        this.view = view

        val filteredPlacesSource = createFilteredPlacesSource(searchTextChanges)

        view.wirePlacesSource(filteredPlacesSource)
    }

    override fun tryGetKnownPlace(id: Int): Place? = placesRepository.getPlaceById(id)

    override val unknownPlaceErrorMessage: String
        get() = "QR Code refers to an unknown place (try reloading list)"

    private fun createFilteredPlacesSource(
        searchTextChanges: Observable<CharSequence>
    ): Observable<List<Place>> {

        return Observable.combineLatest(
                placesRepository.source, searchTextChanges,
                BiFunction<List<Place>, CharSequence, Pair<List<Place>, CharSequence>> {
                        fullList, filter ->
                    Pair(fullList, filter)
                })
                .doOnEach { IdlingResourceHelper.countingIdlingResource.increment() }
                .observeOn(Schedulers.computation())
                .map { (fullList, filter) ->
                    if (filter.isEmpty())
                        fullList
                    else
                        fullList.filter {
                            it.name.contains(filter, ignoreCase = true) ||
                                    it.address.contains(filter, ignoreCase = true)
                        }
                }
                .onErrorResumeNext { e: Throwable ->
                    Observable.just(e)
                            .observeOn(mainScheduler)
                            .subscribe { error -> reportError(error) }
                    Observable.empty<List<Place>>()
                }
    }

    @MainThread
    fun reportError(e: Throwable) {
        Timber.e(e, "Failed to load")
    }

    @MainThread
    fun unbind() {
        this.view = null
    }
}