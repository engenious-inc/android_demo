package com.github.tarcv.orderme.core.data.response

data class MakeOrderResponse(
    val placeId: Int,
    val idTable: Int,
    val comments: String,
    val created: String,
    val sum: Double,
    val id: Int
)