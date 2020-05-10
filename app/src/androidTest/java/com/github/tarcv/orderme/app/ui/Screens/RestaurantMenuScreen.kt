package com.github.tarcv.orderme.app.ui.Screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.tarcv.orderme.app.R

class RestaurantMenuScreen {

    private val pageTitle: ViewInteraction
        get() = onView(withId(R.id.categories_restaurant_name))

    private val backButton: ViewInteraction
        get() = onView(withId(R.id.back_button))

    init {
        pageTitle.check(matches(isDisplayed()))
    }

    fun selectItem(item: String): RestaurantSelectScreen {
        onView(withText(item)).perform(click())
        return RestaurantSelectScreen()
    }

    fun back() {
        backButton.perform(click())
    }
}