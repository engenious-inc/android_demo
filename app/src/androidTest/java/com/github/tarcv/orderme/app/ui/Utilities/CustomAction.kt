package com.github.tarcv.orderme.app.ui.Utilities

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import android.widget.TextView
import org.hamcrest.Matcher
import java.util.concurrent.atomic.AtomicReference

fun getText(textHolder: AtomicReference<String>): ViewAction {
    return GetTextAction(textHolder)
}

private class GetTextAction(var textHolder: AtomicReference<String>) : ViewAction {
    override fun getDescription(): String {
        return "Get text on the view"
    }

    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isAssignableFrom(TextView::class.java)
    }

    override fun perform(uiController: UiController?, view: View?) {
        val text = (view as TextView).text.toString()
        textHolder.set(text)
    }
}