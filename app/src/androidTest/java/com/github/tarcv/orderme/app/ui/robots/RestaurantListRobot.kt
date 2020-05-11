package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.utils.RecyclerViewMatcher.Companion.recyclerElementCount
import org.hamcrest.Matcher

fun restaurantList(func: RestaurantListRobot.() -> Unit) =
        RestaurantListRobot().apply { func() }

class RestaurantListRobot : BaseRobot() {
    private val qrCodeButtonMatcher: Matcher<View> = withId(R.id.searchBtn)
    private val restaurantRecyclerMatcher: Matcher<View> = withId(R.id.restaurantRecycler)
    private val searchFieldTypeMatcher: Matcher<View> = withId(R.id.searchView)
    private val restaurantTitle: Matcher<View> = withId(R.id.titleText)

    fun selectRestaurant(name: String) = tapRecyclerItem(name, restaurantRecyclerMatcher)
    fun tapOnQrCodeButton() = tapBy(qrCodeButtonMatcher)
    fun checkNumberOfRestaurants(count: Int) = onView(restaurantRecyclerMatcher)
            .check(matches((recyclerElementCount(count))))
    fun typeTextInSearchField(text: String) = typeInText(text, searchFieldTypeMatcher)
    fun getRestaurantTitleText() = getMatcherText(restaurantTitle)
}
