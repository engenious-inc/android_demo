package com.github.tarcv.orderme.app.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.tarcv.orderme.app.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QRCodeTests {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)
//asd
    @Test
    fun qrCodeFromList() {
        onView(withId(R.id.login_later_button))
                .perform(click())

        onView(withId(R.id.searchBtn))
                .perform(click())

        val t = "3_5"

        onView(withId(R.id.qrCodeText))
                .perform(replaceText(t), closeSoftKeyboard())

        onView(withId(R.id.submitButton))
                .perform(click())

        onView(withId(R.id.restaurant_options_recycler))
                .check(matches(isDisplayed()))
    }



}


