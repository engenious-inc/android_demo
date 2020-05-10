package com.github.tarcv.orderme.app.ui.Screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

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