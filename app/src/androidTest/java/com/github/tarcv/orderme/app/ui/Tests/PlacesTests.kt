package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.junit.Rule
import org.junit.Test

class PlacesTests {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    val romanovRest: String = "Romanov"
    val hakkasanRest: String = "Hakkasan"

    @Test
    fun testSearchPlace() {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()

        homeScreen.search(romanovRest)
                .restaurantCellIsDisplayed(romanovRest)
                .restaurantCellIsNotDisplayed(hakkasanRest)
    }
}