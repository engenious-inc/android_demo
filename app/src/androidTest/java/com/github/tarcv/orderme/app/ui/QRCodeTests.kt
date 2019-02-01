package com.github.tarcv.orderme.app.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
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

    @Test
    fun qrCodeFromList() {
        onView(withId(R.id.login_later_button))
                .perform(click())
    }


}