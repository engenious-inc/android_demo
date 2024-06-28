package com.github.tarcv.orderme.app.ui.tests

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.qrCode
import com.github.tarcv.orderme.app.ui.robots.restaurant
import com.github.tarcv.orderme.app.ui.robots.menuCategories
import com.github.tarcv.orderme.app.ui.robots.menuItem
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MenuCategoriesTest : BaseTest() {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)
    private val saladsAndVegetables = "SALADS AND VEGETABLES"
    private val defaultCountOfItems: Int = 0

    @Test(timeout = 1*60*1000L)
    fun verifyDefaultMenuItemsNumberTest() {
        setupMockData()

        Thread.sleep(5000L)
        loginLaterAndOpenMockQR()

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(5000L)
        restaurant {
            selectMenuOption()
            checkMenuIsDisplayed()
        }

        Thread.sleep(5000L)
        menuCategories {
            selectItemFromMenu(saladsAndVegetables)
        }

        menuItem {
            assertEquals(defaultCountOfItems, getItemCountFromMenuCategory(colemanFarmsLittleGems))
            assertEquals(defaultCountOfItems, getItemCountFromMenuCategory(blackAndWhiteSalad))
            assertEquals(defaultCountOfItems, getItemCountFromMenuCategory(octopus))
        }
    }
}
