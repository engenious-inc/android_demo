package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.tarcv.orderme.app.ui.Screens.DialogScreen
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
    val invalidQR = "4_5"
    val errorMsgWrongQR = "Wrong QR code or a code from a different place"

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

    @Test
    fun menuHappyPath() {
        val loginScreen = LoginScreen()

        val homeScreen = loginScreen.loginLater()

        val restaurantScreen = homeScreen.tapRestaurant(republique)

        val qrCodeScreen = restaurantScreen.detectTable()

        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val waiterMenuScreen = restaurantScreen.callAWaiter()
        waiterMenuScreen.bringAMenu()

        Thread.sleep(1000)

        DialogScreen().isSuccessDisplayed()
    }

    @Test
    fun invalidQRCodeSecondEntry() {

        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()

        val qrCodeScreen = homeScreen.search()
        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val restaurantScreen = RestaurantScreen()
        restaurantScreen.isRestaurantDisplayed()

        restaurantScreen.detectTable()

        qrCodeScreen.enterQRCode(invalidQR).submit()

        DialogScreen().isErrorDisplayed(errorMsgWrongQR)
    }
}