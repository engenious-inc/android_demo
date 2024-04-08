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

class MenuCategoriesTest2 : BaseTest() {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)
    private val saladsAndVegetables = "SALADS AND VEGETABLES"
    private val defaultCountOfItems: Int = 0

    @Test
    fun verifyDefaultMenuItemsNumberTest() {
        setupMockData()
        loginLaterAndOpenMockQR()

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        restaurant {
            selectMenuOption()
            checkMenuIsDisplayed()
        }

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
