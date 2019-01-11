package com.github.tarcv.orderme.core.data.request

data class CallWaiterRequest(
    val placeId: Int,
    val idTable: Int,
    val created: String,
    val reason: Int
)