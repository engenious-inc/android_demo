package com.github.tarcv.orderme.core.data.request

data class MakeOrderRequest(
    val placeId: Int,
    val idTable: Int,
    val bucket: Map<String, Int>,
    val comments: String,
    val created: String,
    val sum: Double
)