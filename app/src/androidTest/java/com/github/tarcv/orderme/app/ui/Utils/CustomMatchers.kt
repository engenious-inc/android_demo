package com.github.tarcv.orderme.app.ui.Utils

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

class ImageButtonMatcher private constructor(private val buttonToMatch: Int)
    : BoundedMatcher<View, ImageButton>(ImageButton::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("The ImageButton for the current view equals : $buttonToMatch")
    }

    override fun matchesSafely(item: ImageButton?): Boolean {
        return allOf(isDescendantOfA(isAssignableFrom(View::class.java)),
                withId(buttonToMatch))
                .matches(item)
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
        return Matchers.allOf(isDescendantOfA(isAssignableFrom(RecyclerView::class.java)),
                withText(restaurantName))
                .matches(item)
    }

    companion object {
        fun restaurantNameMatches(restaurantName: String): RecyclerViewItemMatcher {
            return RecyclerViewItemMatcher(restaurantName)
        }
    }
}
