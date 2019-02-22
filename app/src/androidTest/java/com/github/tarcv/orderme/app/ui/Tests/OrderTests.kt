package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.Utilities.getText
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicReference

class OrderTests {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    val validQR = "3_5"
    val restaurantName = "Republique"
    val errorMessage = "QR Code could not be scanned"

    @Test
    fun testCustomAction() {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()

        val qrCodeScreen = homeScreen.search()
        qrCodeScreen.enterQRCode(validQR)
                .submit()

        val restaurantScreen = RestaurantScreen()

        sleep(1000)

        onView(withText("Menu"))
                .perform(click())
        sleep(1000)

        onView(withText("FISH"))
                .perform(click())
        sleep(1000)
        val bucketTextHolder = AtomicReference<String>()
        onView(withId(R.id.bucket_textview))
                .perform(getText(bucketTextHolder))
        Assert.assertEquals(bucketTextHolder.get(), "0.0")
    }
}