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

    private val callAWaiter: ViewInteraction
        get() = onView(withText("Call a waiter"))

    private val menuButton: ViewInteraction
        get() = onView(withText("Menu"))

    private val locationButton: ViewInteraction
        get() = onView(withText("Location"))

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

    fun callAWaiter(): WaiterMenuScreen {
        callAWaiter.perform(click())
        return WaiterMenuScreen()
    }

    fun openMenu(): RestaurantMenuScreen {
        menuButton.perform(click())
        return RestaurantMenuScreen()
    }

    fun openMap() {
        locationButton.perform(click())
    }
}