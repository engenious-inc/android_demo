package com.github.tarcv.orderme.app.ui.menu

import com.github.tarcv.orderme.core.data.entity.Dish

interface RestaurantMenuView {
    fun setDishes(dishes: List<Dish>)
    fun refreshSum()
}