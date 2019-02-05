package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.github.tarcv.orderme.app.R

class HomeScreen {
    private val searchButton: ViewInteraction
        get() = onView(withId(R.id.searchBtn))

    private val restaurantCell: (String) -> ViewInteraction = { restaurantName ->
        onView(withText(restaurantName))
    }

    fun search(): MockQRCodeScreen {
        searchButton.perform(click())
        return MockQRCodeScreen()
    }

    fun tapRestaurant(restaurantName: String): RestaurantScreen {
        restaurantCell(restaurantName).perform(click())
        return RestaurantScreen()
    }
}