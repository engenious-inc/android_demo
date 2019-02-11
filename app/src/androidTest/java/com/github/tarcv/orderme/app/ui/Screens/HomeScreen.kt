package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.Utils.ImageButtonMatcher
import com.github.tarcv.orderme.app.ui.Utils.RecyclerViewItemMatcher

class HomeScreen {

    private val searchButton: ViewInteraction
        get() = onView(ImageButtonMatcher.imageButtonMatches(R.id.searchBtn))

    private val restaurantRow: (String) -> ViewInteraction = { restaurantName ->
        onView(RecyclerViewItemMatcher.restaurantNameMatches(restaurantName))
    }

    init {
        searchButton.check(matches(isDisplayed()))
    }

    fun search(): MockQRCodeScreen {
        searchButton.perform(click())
        return MockQRCodeScreen()
    }

    fun tapRestaurant(restaurantName: String): RestaurantScreen {
        restaurantRow(restaurantName).perform(click())

        return RestaurantScreen()
    }
}