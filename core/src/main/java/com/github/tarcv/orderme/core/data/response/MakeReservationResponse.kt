package com.github.tarcv.orderme.core.data.response

data class MakeReservationResponse(
    val id: Int,
    val date: String,
    val created: String,
    val numberOfPeople: Int,
    val userId: Int,
    val phoneNumber: String,
    val placeId: Int
)