package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.github.tarcv.orderme.app.R

class LoginScreen {

    private val loginLaterButton: ViewInteraction
        get() = onView(ViewMatchers.withId(R.id.login_later_button))

    init {
        loginLaterButton.check(matches(isDisplayed()))
    }

    fun clickOnLoginLaterButton() : HomeScreen {
        loginLaterButton.perform(click())
        return HomeScreen()
    }
}