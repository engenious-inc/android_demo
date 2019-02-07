package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R

class MockQRCodeScreen {

    private val qrCodeField: ViewInteraction
        get() = onView(withId(R.id.qrCodeText))

    private val submitButton: ViewInteraction
        get() = onView(withId(R.id.submitButton))

    init {
        qrCodeField.check(matches(isDisplayed()))
    }

    fun enter(qrCode: String) : MockQRCodeScreen {
        qrCodeField.perform(replaceText(qrCode), closeSoftKeyboard())
        return this
    }

    fun clickOnSubmitButton() {
        submitButton.perform(click())
    }

}