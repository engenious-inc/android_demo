
package com.github.tarcv.orderme.app.ui.tests

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import com.github.tarcv.orderme.app.ui.robots.restaurantList
import com.github.tarcv.orderme.app.ui.robots.qrCode
import com.github.tarcv.orderme.app.ui.robots.restaurant
import com.github.tarcv.orderme.app.ui.robots.menuCategories
import com.github.tarcv.orderme.app.ui.robots.menuItem
import junit.framework.TestCase.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class RestaurantMenuTest8 : BaseTest() {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)
    val validQR = "3_5"
    var bucketTotal: Double = 77.0

    @Ignore
    @Test
    fun verifyShoppingCartTotalTest() {
        setupMockData()
        login {
            loginLater()
        }
        restaurantList {
            tapOnQrCodeButton()
        }
        qrCode {
            typeInNewQrCode(validQR)
            tapOnSubmitButton()
        }
        restaurant {
            tapOnMenuOption()
        }
        menuCategories {
            tapSaladsAndVegetables()
        }
        menuItem {
            tapOnPlusButton(colemanFarmsLittleGems)
            tapOnPlusButton(blackAndWhiteSalad)
            tapOnPlusButton(octopus)
            assertEquals(bucketTotal, getBucketTotal())
        }
    }
}
