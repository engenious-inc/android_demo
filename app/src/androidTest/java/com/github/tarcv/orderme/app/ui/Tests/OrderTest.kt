package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import com.github.tarcv.orderme.app.ui.Screens.RestaurantSelectScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class OrderTest : BaseTest() {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)
    val menuSaladsAndVegetables = "SALADS AND VEGETABLES"
    val menuFish = "FISH"
    val colemanFarmsLittleGemsDish = "COLEMAN FARMS LITTLE GEMS"
    val blackWhiteSaladDish = "BLACK & WHITE SALAD"
    val octopusDish = "OCTOPUS"
    val channelIslandsRockFishDish = "CHANNEL ISLANDS ROCK FISH"

    @Test
    fun testCustomAction() {
        val qrCodeScreen = loginLaterAndNavigateToMockQR()
        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()
        // sleep(1000)
        val restaurantMenuScreen = RestaurantScreen().openMenu()
        // sleep(1000)
        restaurantMenuScreen.selectItem(menuFish)
        // sleep(1000)
        RestaurantSelectScreen().getBucketValue()
    }

    @Test
    fun clickOnPlusButton() {
        val qrCodeScreen = loginLaterAndNavigateToMockQR()
        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()

        val restaurantScreen = RestaurantScreen()
        val restaurantMenuScreen = restaurantScreen.openMenu()
        val restaurantSelectScreen = restaurantMenuScreen.selectItem(menuSaladsAndVegetables)

        restaurantSelectScreen.addToCart(octopusDish)
        restaurantSelectScreen.addToCart(colemanFarmsLittleGemsDish)
        restaurantSelectScreen.addToCart(octopusDish)
    }

    @Test
    fun cartSumValidator() {
        var price = 0.0
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()
        val qrCodeScreen = homeScreen.search()
        qrCodeScreen.enterQRCode(republiqueQRCode)
                .submit()
        val restaurantScreen = RestaurantScreen()
        val restaurantMenuScreen = restaurantScreen.openMenu()
        val restaurantSelectScreen = restaurantMenuScreen.selectItem(menuSaladsAndVegetables)

        restaurantSelectScreen.addToCart(colemanFarmsLittleGemsDish)
        price += restaurantSelectScreen.getItemPrice(colemanFarmsLittleGemsDish)
        restaurantSelectScreen.addToCart(blackWhiteSaladDish)
        price += restaurantSelectScreen.getItemPrice(blackWhiteSaladDish)
        restaurantSelectScreen.addToCart(octopusDish)
        price += restaurantSelectScreen.getItemPrice(octopusDish)

        restaurantMenuScreen.back()
        restaurantMenuScreen.selectItem(menuFish)
        restaurantSelectScreen.addToCart(channelIslandsRockFishDish)
        price += restaurantSelectScreen.getItemPrice(channelIslandsRockFishDish)

        val bucketValue = restaurantSelectScreen.getBucketValue()
        assertEquals(price, bucketValue, 0.0)
    }
}