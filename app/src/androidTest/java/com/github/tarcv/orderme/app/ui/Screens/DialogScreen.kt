package com.github.tarcv.orderme.app.ui.Screens

import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers

class DialogScreen {
    private val errorMessage: (String) -> ViewInteraction = { errorMessage ->
        Espresso.onView(ViewMatchers.withText(errorMessage))
    }

    fun isErrorDisplayed(errorMessage: String) {
        errorMessage(errorMessage).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}