package com.github.tarcv.orderme.app.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
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
    val validQR = "3_5"
    val restaurantName = "Republique"
    val errorMessage = "QR Code could not be scanned"

    @Test
    fun qrCodeFromList() {
        onView(withId(R.id.login_later_button))
                .perform(click())

        onView(withId(R.id.searchBtn))
                .perform(click())

        onView(withId(R.id.qrCodeText))
                .perform(replaceText(validQR), closeSoftKeyboard())

        onView(withId(R.id.submitButton))
                .perform(click())

        onView(withId(R.id.restaurant_options_recycler))
                .check(matches(isDisplayed()))
    }

    @Test
    fun qrCodeFromPlaceHappyPath() {
        onView(withId(R.id.login_later_button))
                .perform(click())

        Thread.sleep(2000)

        onView(withText(restaurantName)).perform(click())

        onView(withText("Detect table")).perform(click())

        onView(withId(R.id.qrCodeText))
                .perform(replaceText(validQR), closeSoftKeyboard())

        onView(withId(R.id.submitButton))
                .perform(click())

        onView(withText(restaurantName)).check(matches(isDisplayed()))
    }

    @Test
    fun qrCodeFromErrorSimulation() {
        onView(withId(R.id.login_later_button))
                .perform(click())

        Thread.sleep(2000)

        onView(withText(restaurantName)).perform(click())

        onView(withText("Detect table")).perform(click())

        onView((withId(R.id.errorButton))).perform(click())

        onView(withText(errorMessage)).check(matches(isDisplayed()))
    }
}