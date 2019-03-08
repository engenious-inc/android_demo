package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
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