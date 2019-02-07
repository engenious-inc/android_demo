package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R

class RestaurantScreen {

    val options: ViewInteraction
        get() = onView(withId(R.id.restaurant_options_recycler))

    val optionsAreDisplayed: ViewInteraction
        get() = options.check(matches(isDisplayed()))

    init {
        options.check(matches(isDisplayed()))
    }
}