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

class QRTest43 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun checkRestaurantTitleTest() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            selectRestaurant(republique)
        }

        restaurant {
            checkTitleIsDispayed()
            assertEquals(republique, getRestaurantTitleText())
        }
    }

    @Test
    fun bringMenuHappyPathTest() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            selectRestaurant(republique)
        }

        restaurant {
            tapOnDetectTable()
        }

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        restaurant {
            selectCallWaiter()
        }

        callWaiterOption {
            selectBringMenuOption()
        }

        popUp {
            checkQRCodeSuccessMessage()
        }
    }

    @Test
    fun qrCodeFromPlaceHappyPath() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            selectRestaurant(republique)
        }

        restaurant {
            tapOnDetectTable()
        }

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        restaurant {
            tapOnMenuOption()
            checkMenuIsDisplayed()
        }
    }

    @Test
    fun invalidQrCodeFromSecondEntry() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            tapOnQrCodeButton()
        }

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        restaurant {
            checkTitleIsDispayed()
            backButtonIsDispayed()
            tapOnDetectTable()
        }

        qrCode {
            typeInNewQrCode(inValidQR)
            tapOnSubmitButton()
            errorPopUpIsDispalyed()
        }
    }

    @Test
    fun verifyScanErrorForDetectTableOption() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            selectRestaurant(oceanSeafood)
        }

        restaurant {
            tapOnDetectTable()
        }

        qrCode {
            simulateError()
        }

        popUp {
            checkQRCodeErrorMessage()
        }
    }
}
