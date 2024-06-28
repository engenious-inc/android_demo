package com.github.tarcv.orderme.app.ui.tests.duplicates

import com.github.tarcv.orderme.app.ui.tests.BaseTest

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import com.github.tarcv.orderme.app.ui.robots.qrCode
import com.github.tarcv.orderme.app.ui.robots.restaurant
import com.github.tarcv.orderme.app.ui.robots.restaurantList
import com.github.tarcv.orderme.app.ui.robots.menuCategories
import com.github.tarcv.orderme.app.ui.robots.bucket
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotSame

import org.junit.Rule
import org.junit.Test

class RestaurantListTest42 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    private var numberOfRestPresent: Int = 1
    private var cartTotal: Double = 0.0
    private val numberOfRestaurants = 6

    @Test(timeout = 1*60*1000L)
    fun verifySearchTest() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            typeTextInSearchField(romanov)
            assertNotSame(hakkasan, getRestaurantTitleText())
            checkNumberOfRestaurants(numberOfRestPresent)
            assertEquals(romanov, getRestaurantTitleText())
        }
    }
    @Test(timeout = 1*60*1000L)
    fun checkNumberOfRestaurants() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            checkNumberOfRestaurants(numberOfRestaurants)
        }
    }

    @Test(timeout = 1*60*1000L)
    fun checkDefaultCartValue() {
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
            selectMenuOption()
        }

        Thread.sleep(5000L)
        menuCategories {
            selectFish()
        }

        Thread.sleep(5000L)
        bucket {
            assertEquals(cartTotal, getBucketTotal())
        }
    }
}
