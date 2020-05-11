package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.utils.CustomFailureHandler
import com.github.tarcv.orderme.app.ui.utils.getText
import org.hamcrest.Matcher
import java.util.concurrent.atomic.AtomicReference

open class BaseRobot {
    private val bottomMenuReservationsMatcher: Matcher<View> = withId(R.id.navigation_reservation)
    private val bottomMenuOrdersMatcher: Matcher<View> = withId(R.id.navigation_orders)

    fun tapOnReservations() = tapBy(bottomMenuReservationsMatcher)
    fun tapOnOrders() = tapBy(bottomMenuOrdersMatcher)

    fun tapBy(matcher: Matcher<View>): ViewInteraction = onView(matcher)
            .withFailureHandler(CustomFailureHandler)
            .perform(click())
    fun isDisplayedBy(matcher: Matcher<View>): ViewInteraction =
            onView(matcher).check(matches(isDisplayed()))

    fun tapRecyclerItem(name: String, matcher: Matcher<View>) {
        onView(matcher)
                .perform(actionOnItem<RecyclerView.ViewHolder>
                (hasDescendant(withText(name)), scrollTo()))
        onView(withText(name)).perform(click())
    }

    fun typeInText(text: String, matcher: Matcher<View>): ViewInteraction =
            onView(matcher).perform(typeText(text), closeSoftKeyboard())

    fun sleep(time: Long = 2000) = Thread.sleep(time)

    fun getMatcherText(matcher: Matcher<View>): String {
        val restTextReference: AtomicReference<String> = AtomicReference()
        onView(matcher)
                .withFailureHandler(CustomFailureHandler)
                .perform(getText(restTextReference))
        return restTextReference.toString()
    }
}
