package com.github.tarcv.orderme.app.ui.tests

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

class RestaurantListTest : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    private var numberOfRestPresent: Int = 1
    private var cartTotal: Double = 0.0
    private val numberOfRestaurants = 6

    @Test
    fun verifySearchTest() {
        setupMockData()

        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            typeTextInSearchField(romanov)
            assertNotSame(hakkasan, getRestaurantTitleText())
            checkNumberOfRestaurants(numberOfRestPresent)
            assertEquals(romanov, getRestaurantTitleText())
        }
    }
    @Test
    fun checkNumberOfRestaurants() {
        setupMockData()

        Thread.sleep(1000L)
        login {
            loginLater()
        }

        Thread.sleep(1000L)
        restaurantList {
            checkNumberOfRestaurants(numberOfRestaurants)
        }
    }

    @Test
    fun checkDefaultCartValue() {
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
            selectMenuOption()
        }

        Thread.sleep(1000L)
        menuCategories {
            selectFish()
        }

        Thread.sleep(1000L)
        bucket {
            assertEquals(cartTotal, getBucketTotal())
        }
    }
}
