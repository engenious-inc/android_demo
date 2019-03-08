package com.github.tarcv.orderme.app.ui.Tests

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.intent.matcher.UriMatchers.hasScheme
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

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
        sleep(500)

        intended(
                allOf(
                        hasAction(equalTo(Intent.ACTION_VIEW)),
                        hasData(hasScheme("geo"))
                )
        )
    }
}