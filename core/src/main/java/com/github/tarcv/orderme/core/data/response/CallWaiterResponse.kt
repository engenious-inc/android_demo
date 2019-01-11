package com.github.tarcv.orderme.core.data.response

data class CallWaiterResponse(
    val id: Int,
    val placeId: Int,
    val idTable: Int,
    val created: String,
    val reason: Int
)