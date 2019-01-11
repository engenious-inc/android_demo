package com.github.tarcv.orderme.app

import com.github.tarcv.orderme.core.data.entity.Dish

class Bucket {
    var dishes = mutableMapOf<Dish, Int>()
}