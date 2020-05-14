package com.github.tarcv.orderme.app.ui.utils

import android.view.View
import androidx.test.espresso.EspressoException
import androidx.test.espresso.FailureHandler
import org.hamcrest.Matcher
import java.lang.AssertionError

object CustomFailureHandler : FailureHandler {
    override fun handle(error: Throwable?, viewMatcher: Matcher<View>?) {
        if (error is EspressoException || error is AssertionError) {
            error.stackTrace = Thread.currentThread().stackTrace
                    .filter { it.className.startsWith("com.github.tarcv") }
                    .toTypedArray()
        }
        throw error!!
    }
}
