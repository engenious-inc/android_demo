package com.github.tarcv.orderme.app.ui.menuCategory

import com.github.tarcv.orderme.core.data.entity.Category

interface RestaurantMenuCategoriesView {
    fun setCategories(categories: List<Category>)
}