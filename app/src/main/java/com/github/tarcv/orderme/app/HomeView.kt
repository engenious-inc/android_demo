package com.github.tarcv.orderme.app

import com.github.tarcv.orderme.core.data.entity.Place
import io.reactivex.Observable

interface HomeView {
    fun wirePlacesSource(source: Observable<List<Place>>)
}