package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText

class DialogScreen {
    private val errorMessage: (String) -> ViewInteraction = { errorMessage ->
        onView(withText(errorMessage))
    }

    private val success: ViewInteraction
        get() = onView(withText("Success!"))

    fun isErrorDisplayed(errorMessage: String) {
        errorMessage(errorMessage).check(matches(isDisplayed()))
    }

    fun isSuccessDisplayed() {
        success.check(matches(isDisplayed()))
    }
}