package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.github.tarcv.orderme.app.R

class RestaurantScreen {
    private val restaurantOptions: ViewInteraction
        get() = onView(withId(R.id.restaurant_options_recycler))

    private val detectTableButton: ViewInteraction
        get() = onView(withText("Detect table"))

    init {
        restaurantOptions.check(matches(isDisplayed()))
    }
  
    fun isRestaurantDisplayed() {
        restaurantOptions.check(matches(isDisplayed()))
    }

    fun detectTable(): MockQRCodeScreen {
        detectTableButton.perform(click())
        return MockQRCodeScreen()
}