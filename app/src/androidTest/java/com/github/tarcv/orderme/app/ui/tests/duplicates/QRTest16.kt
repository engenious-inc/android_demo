package com.github.tarcv.orderme.app.ui.tests.duplicates

import com.github.tarcv.orderme.app.ui.tests.BaseTest

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

class QRTest16 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun checkRestaurantTitleTest() {
        setupMockData()
        Thread.sleep(3000L)
        login {
            loginLater()
        }

        Thread.sleep(3000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(3000L)
        restaurant {
            checkTitleIsDispayed()
            assertEquals(republique, getRestaurantTitleText())
        }
    }

    @Test
    fun bringMenuHappyPathTest() {
        setupMockData()

        Thread.sleep(1500L)
        login {
            loginLater()
        }

        Thread.sleep(1500L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(1500L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(1500L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(1500L)
        restaurant {
            selectCallWaiter()
        }

        Thread.sleep(1500L)
        callWaiterOption {
            selectBringMenuOption()
        }

        Thread.sleep(1500L)
        popUp {
            checkQRCodeSuccessMessage()
        }
    }

    @Test
    fun qrCodeFromPlaceHappyPath() {
        setupMockData()

        Thread.sleep(1500L)
        login {
            loginLater()
        }

        Thread.sleep(1500L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(1500L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(1500L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(1500L)
        restaurant {
            tapOnMenuOption()
            checkMenuIsDisplayed()
        }
    }

    @Test
    fun invalidQrCodeFromSecondEntry() {
        setupMockData()

        Thread.sleep(1500L)
        login {
            loginLater()
        }

        Thread.sleep(1500L)
        restaurantList {
            tapOnQrCodeButton()
        }

        Thread.sleep(1500L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(1500L)
        restaurant {
            checkTitleIsDispayed()
            backButtonIsDispayed()
            tapOnDetectTable()
        }

        Thread.sleep(1500L)
        qrCode {
            typeInNewQrCode(inValidQR)
            tapOnSubmitButton()
            errorPopUpIsDispalyed()
        }
    }

    @Test
    fun verifyScanErrorForDetectTableOption() {
        setupMockData()

        Thread.sleep(1500L)
        login {
            loginLater()
        }

        Thread.sleep(1500L)
        restaurantList {
            selectRestaurant(oceanSeafood)
        }

        Thread.sleep(1500L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(1500L)
        qrCode {
            simulateError()
        }

        Thread.sleep(1500L)
        popUp {
            checkQRCodeErrorMessage()
        }
    }
}
