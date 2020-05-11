package com.github.tarcv.orderme.app.ui.robots

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import android.view.View
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import com.github.tarcv.orderme.app.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

fun orders(func: OrdersSummaryRobot.() -> Unit) =
        OrdersSummaryRobot().apply { func() }

class OrdersSummaryRobot : BaseRobot() {
    private val orderRecyclerViewMatcher: Matcher<View> = withId(R.id.order_recyclerview)

    fun checkOrderIsDisplayed
            (restaurantName: String, orderDate: String, orderTime: String, bucketTotal: String) {
        val orderMatcher = allOf(
                hasDescendant(allOf(withId(R.id.order_restaurant), withText(restaurantName))),
                hasDescendant(allOf(withId(R.id.order_date), withText(orderDate))),
                hasDescendant(allOf(withId(R.id.order_time), withText(orderTime))),
                hasDescendant(allOf(withId(R.id.order_check), withText("$$bucketTotal")))
        )
        onView(orderRecyclerViewMatcher)
                .perform(actionOnItem<RecyclerView.ViewHolder>
                (orderMatcher, scrollTo()))
                .check(matches(isDisplayed()))
    }
}
