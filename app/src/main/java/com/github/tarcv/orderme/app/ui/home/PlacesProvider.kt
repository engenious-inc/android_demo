package com.github.tarcv.orderme.app.ui.home

import com.github.tarcv.orderme.core.data.entity.Place

interface PlacesProvider {
    val unknownPlaceErrorMessage: String

    fun tryGetKnownPlace(id: Int): Place?
}
