package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.github.tarcv.orderme.app.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

class HomeScreen {
    private val searchButton: ViewInteraction
        get() = onView(ImageButtonMatcher.imageButtonMatches(R.id.searchBtn))
        //get() = onView(withButton("searchBtn"))
        //get() = onView(withId(R.id.searchBtn))

    /*private fun withButton(buttonId: String): Matcher<View> {
        return object : BoundedMatcher<View, ImageButton>(ImageButton::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("is image button with ID: $buttonId")
            }

            override fun matchesSafely(item: ImageButton?): Boolean {
                return buttonId == item?.id.toString()
            }

        }
    }

    private fun withRestaurantName(expectedRestaurantName: String): Matcher<View> {
        return object: TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("is isDescendantOfA RecyclerView with text : $expectedRestaurantName")
            }

            override fun matchesSafely(item: View?): Boolean {
                return allOf(isDescendantOfA(isAssignableFrom(RecyclerView::class.java)),withText(expectedRestaurantName)).matches(item)
            }
        }
    }*/

    init {
        searchButton.check(matches(isDisplayed()))
    }

    fun search(): MockQRCodeScreen {
        searchButton.perform(click())
        return MockQRCodeScreen()
    }

    fun tapRestaurant(restaurantName: String): RestaurantScreen {
        //onView(withRestaurantName(restaurantName)).perform(click());
        onView(RecyclerViewItemMatcher.restaurantNameMatches(restaurantName))
                .perform(click())

        return RestaurantScreen()
    }
}


class ImageButtonMatcher private constructor(private val buttonToMatch: Int)
    : BoundedMatcher<View, ImageButton>(ImageButton::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("The ImageButton for the current view equals : $buttonToMatch")
    }

    override fun matchesSafely(item: ImageButton?): Boolean {
        return allOf(isDescendantOfA(isAssignableFrom(View::class.java)), withId(buttonToMatch)).matches(item)
    }

    companion object {
        fun imageButtonMatches(buttonToMatch: Int): ImageButtonMatcher {
            return ImageButtonMatcher(buttonToMatch)
        }
    }
}

class RecyclerViewItemMatcher private constructor(private val restaurantName: String)
    : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        description?.appendText("The item text for the RecyclerView equals : $restaurantName")
    }

    override fun matchesSafely(item: View?): Boolean {
        return allOf(isDescendantOfA(isAssignableFrom(RecyclerView::class.java)),withText(restaurantName)).matches(item)
    }

    companion object {
        fun restaurantNameMatches(restaurantName: String): RecyclerViewItemMatcher {
            return RecyclerViewItemMatcher(restaurantName)
        }
    }

}