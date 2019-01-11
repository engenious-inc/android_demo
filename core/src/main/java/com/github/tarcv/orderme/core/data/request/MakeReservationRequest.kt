package com.github.tarcv.orderme.core.data.request

import com.github.tarcv.orderme.core.data.entity.Reserve

data class MakeReservationRequest(
    val placeId: Int,
    val date: String,
    val created: String,
    val phoneNumber: String,
    val numberOfPeople: Int
) {

    constructor(reserve: Reserve) : this(
            reserve.placeId,
            reserve.date,
            reserve.created,
            reserve.phoneNumber,
            reserve.numberOfPeople)
}