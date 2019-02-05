package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R

class LoginScreen {
    private val loginLaterButton: ViewInteraction
        get() = onView(withId(R.id.login_later_button))

    fun loginLater(): HomeScreen {
        loginLaterButton.perform(click())
        return HomeScreen()
    }
}