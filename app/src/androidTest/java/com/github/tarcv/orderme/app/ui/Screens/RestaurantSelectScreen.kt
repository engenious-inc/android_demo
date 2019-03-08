package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.hasSibling
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matchers.allOf
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.Utilities.getText
import java.util.concurrent.atomic.AtomicReference

class RestaurantSelectScreen {

    private val pageTitle: ViewInteraction
        get() = onView(withId(R.id.category_name))

    private val bucketTextHolder = AtomicReference<String>()

    private val bucket: ViewInteraction
        get() = onView(withId(R.id.bucket_textview))

    private val itemPrice = AtomicReference<String>()

init {
    pageTitle.check(matches(isDisplayed()))
}

    fun addToCart(dish: String) {
        onView(
                allOf(
                        withId(R.id.plus_button),
                        isDescendantOfA(hasSibling(withText(dish)))
                )
        ).perform(click())
    }

    fun getBucketValue(): Double {
        bucket.perform(getText(bucketTextHolder))
        return bucketTextHolder.toString().toDouble()
    }

    fun getItemPrice(itemName: String): Double {
        onView(
                allOf(
                        withId(R.id.menu_item_price),
                        hasSibling(withText(itemName))
                )
        ).perform(getText(itemPrice))
        return itemPrice.toString().replace("$ ", "").toDouble()
    }
}