package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R

class MockQRCodeScreen {
    private val qrCodeText: ViewInteraction
        get() = onView(withId(R.id.qrCodeText))

    private val submitButton: ViewInteraction
        get() = onView(withId(R.id.submitButton))

    private val errorButton: ViewInteraction
        get() = onView(withId(R.id.errorButton))

    fun enterQRCode(validCode: String): MockQRCodeScreen {
        qrCodeText.perform(replaceText(validCode), closeSoftKeyboard())
        return this
    }

    fun submit() {
        submitButton.perform(click())
    }

    fun simulateError(): DialogScreen {
        errorButton.perform(click())
        return DialogScreen()
    }
}