package com.github.tarcv.orderme.core.data.entity

data class Dish(
    var id: Int,
    var description: String,
    var name: String,
    var categoryId: Int,
    var price: Double
)