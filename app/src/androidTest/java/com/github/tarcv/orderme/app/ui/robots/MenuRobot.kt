package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher

import com.github.tarcv.orderme.app.R

fun menuCategories(func: MenuRobot.() -> Unit) = MenuRobot().apply { func() }

class MenuRobot : BaseRobot() {

    private val categoriesRecyclerMatcher: Matcher<View> = withId(R.id.categories_recycler)
    private val saladsAndVegetablesMatcher: Matcher<View> =
            withText("SALADS AND VEGETABLES")
    fun tapSaladsAndVegetables() = tapBy(saladsAndVegetablesMatcher)
    private val fishMatcher: Matcher<View> = withText("FISH")

    fun selectItemFromMenu(name: String) = tapRecyclerItem(name, categoriesRecyclerMatcher)
    fun selectFish() = tapBy(fishMatcher)
}
