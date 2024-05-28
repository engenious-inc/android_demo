package com.github.tarcv.orderme.app.ui.tests

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import com.github.tarcv.orderme.app.ui.robots.restaurantList
import com.github.tarcv.orderme.app.ui.robots.restaurant
import com.github.tarcv.orderme.app.ui.robots.qrCode
import com.github.tarcv.orderme.app.ui.robots.callWaiterOption
import com.github.tarcv.orderme.app.ui.robots.popUp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class QRTest : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun checkRestaurantTitleTest() {
        setupMockData()
        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(1000L)
        restaurant {
            checkTitleIsDispayed()
            assertEquals(republique, getRestaurantTitleText())
        }
    }

    @Test
    fun bringMenuHappyPathTest() {
        setupMockData()

        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(1000L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(1000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(1000L)
        restaurant {
            selectCallWaiter()
        }

        Thread.sleep(1000L)
        callWaiterOption {
            selectBringMenuOption()
        }

        Thread.sleep(1000L)
        popUp {
            checkQRCodeSuccessMessage()
        }
    }

    @Test
    fun qrCodeFromPlaceHappyPath() {
        setupMockData()

        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(1000L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(1000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(1000L)
        restaurant {
            tapOnMenuOption()
            checkMenuIsDisplayed()
        }
    }

    @Test
    fun invalidQrCodeFromSecondEntry() {
        setupMockData()

        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            tapOnQrCodeButton()
        }

        Thread.sleep(1000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(1000L)
        restaurant {
            checkTitleIsDispayed()
            backButtonIsDispayed()
            tapOnDetectTable()
        }

        Thread.sleep(1000L)
        qrCode {
            typeInNewQrCode(inValidQR)
            tapOnSubmitButton()
            errorPopUpIsDispalyed()
        }
    }

    @Test
    fun verifyScanErrorForDetectTableOption() {
        setupMockData()

        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            selectRestaurant(oceanSeafood)
        }

        Thread.sleep(1000L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(1000L)
        qrCode {
            simulateError()
        }

        Thread.sleep(1000L)
        popUp {
            checkQRCodeErrorMessage()
        }
    }
}
