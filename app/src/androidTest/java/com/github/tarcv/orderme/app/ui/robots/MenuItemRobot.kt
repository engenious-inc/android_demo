package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import com.github.tarcv.orderme.app.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

fun menuItem(func: MenuItemRobot.() -> Unit) = MenuItemRobot().apply { func() }

class MenuItemRobot : BaseRobot() {

    private val itemCountMatcher: Matcher<View> = withId(R.id.menu_count)
    private val plusButtonMatcher: Matcher<View> = withId(R.id.plus_button)
    private val totalBucketMatcher: Matcher<View> = withId(R.id.bucket_textview)
    private val shoppingCartMatcher: Matcher<View> = withId(R.id.bucket_button)

    fun getItemCountFromMenuCategory(menuItemName: String) =
            getMatcherText(allOf(itemCountMatcher, withParent(hasSibling(withText(menuItemName)))))
                    .toInt()
    fun getBucketTotal() = getMatcherText(totalBucketMatcher).toDouble()
    fun tapOnPlusButton(dishName: String) {
        onView(allOf(plusButtonMatcher, isDescendantOfA(hasSibling(withText(dishName)))))
                .perform(click())
    }

    fun tapOnShoppingCart() = tapBy(shoppingCartMatcher)
}
