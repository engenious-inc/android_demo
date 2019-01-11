package com.github.tarcv.orderme.core.data.entity

data class Order(
    val placeId: Int,
    val idTable: Int,
    val bucket: Map<Dish, Int>,
    val comments: String,
    val created: String,
    val sum: Double
)