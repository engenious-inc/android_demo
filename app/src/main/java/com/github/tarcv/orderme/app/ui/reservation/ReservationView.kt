package com.github.tarcv.orderme.app.ui.reservation

import com.github.tarcv.orderme.core.data.entity.Reserve
import io.reactivex.Observable

interface ReservationView {
    fun wireReservationsSource(source: Observable<List<Reserve>>)
}