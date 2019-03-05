package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import android.support.test.espresso.matcher.ViewMatchers.hasSibling
import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.Utilities.getText
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicReference

class OrderTest : BaseTest() {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    val errorMessage = "QR Code could not be scanned"

    fun addToCart(dish: String) {
        onView(
                allOf(
                        withId(R.id.plus_button),
                        isDescendantOfA(hasSibling(withText(dish)))
                )
        ).perform(click())
    }

    @Test
    fun testCustomAction() {
        val qrCodeScreen = loginLaterAndNavigateToMockQR()
        qrCodeScreen.enterQRCode(republiqueQRCode)
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
    }

    @Test
    fun clickOnPlusButton() {
        val qrCodeScreen = loginLaterAndNavigateToMockQR()
        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val restaurantScreen = RestaurantScreen()

        sleep(1000)

        onView(withText("Menu"))
                .perform(click())
        sleep(1000)

        onView(withText("SALADS AND VEGETABLES"))
                .perform(click())
        sleep(1000)

        addToCart("OCTOPUS")
        addToCart("COLEMAN FARMS LITTLE GEMS")
        addToCart("OCTOPUS")
    }
}