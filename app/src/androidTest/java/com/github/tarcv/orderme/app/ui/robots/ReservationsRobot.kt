package com.github.tarcv.orderme.app.ui.robots

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.github.tarcv.orderme.app.R
import android.view.View
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

fun reservations(func: ReservationsRobot.() -> Unit) = ReservationsRobot().apply { func() }

class ReservationsRobot : BaseRobot() {
    private val futureReservationsMatcher: Matcher<View> = withText("Future reservations")
    private val reservationDateMatcher: Matcher<View> = withId(R.id.reservation_date)
    private val reservationTimeMatcher: Matcher<View> = withId(R.id.reservation_time)

    private val reservationRestaurantMatcher: Matcher<View> = withId(R.id.reservation_restaurant)
    private val reservationRecycleMatcher: Matcher<View> = withId(R.id.reservation_recycler)

    fun tapFutureReservations() = tapBy(futureReservationsMatcher)

    fun isReservationDisplayed(restName: String, date: String, time: String) {
        val reservationMatcher: Matcher<View> = allOf(
                hasDescendant(allOf(reservationRestaurantMatcher, withText(restName))),
                hasDescendant(allOf(reservationDateMatcher, withText(date))),
                hasDescendant(allOf(reservationTimeMatcher, withText(time)))
        )
        onView(reservationRecycleMatcher)
                .perform(actionOnItem<RecyclerView.ViewHolder>
                (reservationMatcher, scrollTo()))
                .check(matches(isDisplayed()))
    }
}
