package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.junit.Rule
import org.junit.Test

class PlacesTests : BaseTest() {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun testSearchPlace() {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()

        homeScreen.search(romanovRest)
                .restaurantCellIsDisplayed(romanovRest)
                .restaurantCellIsNotDisplayed(hakkasanRest)
    }
}