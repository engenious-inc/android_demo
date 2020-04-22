package com.github.tarcv.orderme.app.ui.Screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R

class LoginScreen {
    private val loginLaterButton: ViewInteraction
        get() = onView(withId(R.id.login_later_button))

    init {
        loginLaterButton.check(matches(isDisplayed()))
    }

    fun loginLater(): HomeScreen {
        loginLaterButton.perform(click())
        return HomeScreen()
    }
}