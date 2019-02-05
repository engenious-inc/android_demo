package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.github.tarcv.orderme.app.R

class MockQRCodeScreen {
    private val qrCodeText: ViewInteraction
        get() = onView(withId(R.id.qrCodeText))

    private val submitButton: ViewInteraction
        get() = onView(withId(R.id.submitButton))

    private val errorButton: ViewInteraction
        get() = onView(withId(R.id.errorButton))

    private val errorMessage: (String) -> ViewInteraction = { errorMessage ->
        onView(withText(errorMessage))
    }

    fun enterQRCode(validCode: String): MockQRCodeScreen {
        qrCodeText.perform(replaceText(validCode), closeSoftKeyboard())
        return this
    }

    fun submit() {
        submitButton.perform(click())
    }

    fun simulateError() {
        errorButton.perform(click())
    }

    fun isErrorDisplayed(errorMessage: String) {
        errorMessage(errorMessage).check(matches(isDisplayed()))
    }
}