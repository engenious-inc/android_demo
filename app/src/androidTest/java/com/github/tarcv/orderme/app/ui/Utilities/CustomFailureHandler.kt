package com.github.tarcv.orderme.app.ui.Utilities

import androidx.test.espresso.EspressoException
import androidx.test.espresso.FailureHandler
import android.view.View
import org.hamcrest.Matcher

object CustomFailureHandler : FailureHandler {

    override fun handle(error: Throwable, viewMatcher: Matcher<View>) {
        if (error is EspressoException || error is AssertionError) {
            error.stackTrace = Thread.currentThread().stackTrace
                    .filter { it.className.startsWith("com.github.tarcv") }
                    .toTypedArray()
        }
        throw error
    }
}