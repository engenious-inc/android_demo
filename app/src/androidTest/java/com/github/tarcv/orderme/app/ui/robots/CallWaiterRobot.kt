package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher

fun callWaiterOption(func: CallWaiterRobot.() -> Unit) = CallWaiterRobot().apply { func() }

class CallWaiterRobot : BaseRobot() {
    private val bringMenuMatcher: Matcher<View> = withText("Bring a menu")

    fun selectBringMenuOption() = tapBy(bringMenuMatcher)
}
