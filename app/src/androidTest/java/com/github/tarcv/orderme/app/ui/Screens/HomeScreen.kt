package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R

class HomeScreen {

    private val qrCodeButton: ViewInteraction
        get() = onView(withId(R.id.searchBtn))

    init {
        qrCodeButton.check(matches(isDisplayed()))
    }

    fun clickOnQRCodeButton(): MockQRCodeScreen {
        qrCodeButton.perform(click())
        return MockQRCodeScreen()
    }
}