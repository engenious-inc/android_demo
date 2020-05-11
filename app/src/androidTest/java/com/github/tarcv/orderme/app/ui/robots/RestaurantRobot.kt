package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.tarcv.orderme.app.R
import org.hamcrest.Matcher

fun restaurant(func: RestaurantRobot.() -> Unit) =
        RestaurantRobot().apply { func() }

class RestaurantRobot : BaseRobot() {
    private val restaurantTitleMatcher: Matcher<View> = withId(R.id.restaurant_name)
    private val detectTableMatcher: Matcher<View> = withText("Detect table")
    private val callWaiterMatcher: Matcher<View> = withText("Call a waiter")
    private val backButtonMatcher: Matcher<View> = withId(R.id.back_button)
    private val menuScreenViewMatchers: Matcher<View> = withId(R.id.categories_restaurant_name)
    private val menuOptionMatcher: Matcher<View> = withText("Menu")
    private val mapOptionMatcher: Matcher<View> = withText("Location")
    private val phoneOptionMatcher: Matcher<View> = withText("Phone")
    private val reservationMatcher: Matcher<View> = withText("Reservation")

    fun selectCallWaiter() = tapBy(callWaiterMatcher)
    fun checkTitleIsDispayed() = isDisplayedBy(restaurantTitleMatcher)
    fun backButtonIsDispayed() = isDisplayedBy(backButtonMatcher)
    fun tapOnDetectTable() = tapBy(detectTableMatcher)
    fun checkMenuIsDisplayed() = isDisplayedBy(menuScreenViewMatchers)
    fun selectMenuOption() = tapBy(menuOptionMatcher)
    fun tapOnMenuOption() = tapBy(menuOptionMatcher)
    fun getRestaurantTitleText() = getMatcherText(restaurantTitleMatcher)
    fun tapOnMap() = tapBy(mapOptionMatcher)
    fun tapOnPhone() = tapBy(phoneOptionMatcher)
    fun tapOnReservation() = tapBy(reservationMatcher)
}
