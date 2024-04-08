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

class RestaurantListTest58 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    private var numberOfRestPresent: Int = 1
    private var cartTotal: Double = 0.0
    private val numberOfRestaurants = 6

    @Test
    fun verifySearchTest() {
        setupMockData()
        login {
            loginLater()
        }

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
        login {
            loginLater()
        }

        restaurantList {
            checkNumberOfRestaurants(numberOfRestaurants)
        }
    }

    @Test
    fun checkDefaultCartValue() {
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
            selectMenuOption()
        }

        menuCategories {
            selectFish()
        }

        bucket {
            assertEquals(cartTotal, getBucketTotal())
        }
    }
}
