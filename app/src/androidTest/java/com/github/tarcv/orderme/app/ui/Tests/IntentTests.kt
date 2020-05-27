package com.github.tarcv.orderme.app.ui.tests

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.UriMatchers.hasScheme
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class IntentTests : BaseTest() {

    @get:Rule
    var mActivityTestRule = IntentsTestRule(SplashActivity::class.java)

    @Before
    fun blockGoingToExternalApps() {
        Intents.intending(not(IntentMatchers.isInternal()))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun testOpenMap() {
        val mockQRCodeScreen = loginLaterAndNavigateToMockQR()

        mockQRCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val restaurantScreen = RestaurantScreen()
        restaurantScreen.openMap()

        intended(
                allOf(
                        hasAction(equalTo(Intent.ACTION_VIEW)),
                        hasData(hasScheme("geo"))
                )
        )
    }

    @Test
    fun testOpenPhone() {
        val mockQRCodeScreen = loginLaterAndNavigateToMockQR()

        mockQRCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val restaurantScreen = RestaurantScreen()
        restaurantScreen.openPhone()

        intended(
                allOf(
                        hasAction(equalTo(Intent.ACTION_DIAL)),
                        hasData(hasScheme("tel"))
                )
        )
    }
}