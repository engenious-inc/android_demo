package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.pressImeActionButton
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withChild
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.widget.EditText
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.Utilities.CustomFailureHandler
import com.github.tarcv.orderme.app.ui.Utilities.ImageButtonMatcher
import com.github.tarcv.orderme.app.ui.Utilities.RecyclerViewItemMatcher
import org.hamcrest.CoreMatchers.allOf
import java.lang.Thread.sleep

class HomeScreen {

    private val searchButton: ViewInteraction

        get() = onView(ImageButtonMatcher.imageButtonMatches(R.id.searchBtn))

    private val restaurantRow: (String) -> ViewInteraction = { restaurantName ->
        onView(RecyclerViewItemMatcher.restaurantNameMatches(restaurantName))
    }

    private val searchField: ViewInteraction
        get() = onView(
                allOf(
                        isDescendantOfA(withId(R.id.searchView)),
                        isAssignableFrom(EditText::class.java)
                )
        )

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

    fun search(text: String): HomeScreen {
        searchField.perform(replaceText(text), pressImeActionButton())
        sleep(1000)
        return this
    }

    fun restaurantCellIsDisplayed(name: String): HomeScreen {
        onView(
                allOf(
                        withChild(withId(R.id.titleImage)),
                        hasDescendant(withText(name))
                )
        )
                .withFailureHandler(CustomFailureHandler)
                .check(matches(isDisplayed()))
        return this
    }

    fun restaurantCellIsNotDisplayed(name: String): HomeScreen {
        onView(
                allOf(
                        withChild(withId(R.id.titleImage)),
                        hasDescendant(withText(name))
                )
        )
                .withFailureHandler(CustomFailureHandler)
                .check(doesNotExist())
        return this
    }
}