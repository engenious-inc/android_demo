package com.github.tarcv.orderme.core.data.entity

import java.io.Serializable

class Place(
    var id: Int,
    var name: String,
    var address: String,
    var phone: String,
    var latitude: String,
    var longitude: String,
    var imagePath: String
) : Serializable
