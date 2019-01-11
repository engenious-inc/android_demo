package com.github.tarcv.orderme.core.data.entity

import java.io.Serializable

data class Category(
    var name: String,
    var id: Int,
    var placeId: Int
) : Serializable