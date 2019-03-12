package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withText

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