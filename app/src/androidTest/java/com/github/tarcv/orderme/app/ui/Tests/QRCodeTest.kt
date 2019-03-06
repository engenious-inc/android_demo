package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QRCodeTest : BaseTest() {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    val errorMessage = "QR Code could not be scanned"

    @Test
    fun qrCodeFromList() {
        val qrCodeScreen = loginLaterAndNavigateToMockQR()
        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val restaurantScreen = RestaurantScreen()
        restaurantScreen.isRestaurantDisplayed()
    }

    @Test
    fun qrCodeFromPlaceHappyPath() {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()

        val restaurantScreen = homeScreen.tapRestaurant(republique)

        val qrCodeScreen = restaurantScreen.detectTable()

        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        restaurantScreen.isRestaurantDisplayed()
    }

    @Test
    fun qrCodeFromErrorSimulation() {
        val loginScreen = LoginScreen()

        val homeScreen = loginScreen.loginLater()

        val restaurantScreen = homeScreen.tapRestaurant(republique)

        val qrCodeScreen = restaurantScreen.detectTable()

        val dialogScreen = qrCodeScreen.simulateError()
        dialogScreen.isErrorDisplayed(errorMessage)
    }
}