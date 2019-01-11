package com.github.tarcv.orderme.core.data.response

import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Dish

data class GetMenuResponse(
    var categories: List<Category>,
    var dishes: List<Dish>
)