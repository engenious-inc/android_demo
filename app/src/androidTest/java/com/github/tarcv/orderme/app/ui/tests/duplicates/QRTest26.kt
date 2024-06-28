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

class QRTest26 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test(timeout = 1*60*1000L)
    fun checkRestaurantTitleTest() {
        setupMockData()
        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(5000L)
        restaurant {
            checkTitleIsDispayed()
            assertEquals(republique, getRestaurantTitleText())
        }
    }

    @Test(timeout = 1*60*1000L)
    fun bringMenuHappyPathTest() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(5000L)
        restaurant {
            selectCallWaiter()
        }

        Thread.sleep(5000L)
        callWaiterOption {
            selectBringMenuOption()
        }

        Thread.sleep(5000L)
        popUp {
            checkQRCodeSuccessMessage()
        }
    }

    @Test(timeout = 1*60*1000L)
    fun qrCodeFromPlaceHappyPath() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(republique)
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnMenuOption()
            checkMenuIsDisplayed()
        }
    }

    @Test(timeout = 1*60*1000L)
    fun invalidQrCodeFromSecondEntry() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            tapOnQrCodeButton()
        }

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(5000L)
        restaurant {
            checkTitleIsDispayed()
            backButtonIsDispayed()
            tapOnDetectTable()
        }

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(inValidQR)
            tapOnSubmitButton()
            errorPopUpIsDispalyed()
        }
    }

    @Test(timeout = 1*60*1000L)
    fun verifyScanErrorForDetectTableOption() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(oceanSeafood)
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnDetectTable()
        }

        Thread.sleep(5000L)
        qrCode {
            simulateError()
        }

        Thread.sleep(5000L)
        popUp {
            checkQRCodeErrorMessage()
        }
    }
}
