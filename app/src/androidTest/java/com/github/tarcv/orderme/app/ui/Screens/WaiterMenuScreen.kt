package com.github.tarcv.orderme.app.ui.Screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText

class WaiterMenuScreen {

    private val screenTitle: ViewInteraction
        get() = onView(withText("How waiter can help you?"))

    init {
        screenTitle.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    enum class Options {
        MENU, BILL, CLEAN, HOOKAH, OTHER;

        fun pressOn() {
            when (this) {
                MENU -> findAndPress("Bring a menu")
                BILL -> findAndPress("Bring the bill")
                CLEAN -> findAndPress("Clean the table")
                HOOKAH -> findAndPress("Call a hookah man")
                OTHER -> findAndPress("Other")
            }
        }

        private fun findAndPress(text: String) {
            onView(withText(text)).perform(click())
        }
    }
}