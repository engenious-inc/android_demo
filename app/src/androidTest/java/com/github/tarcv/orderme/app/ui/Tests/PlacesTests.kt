package com.github.tarcv.orderme.app.ui.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.SplashActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class PlacesTests : BaseTest() {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

//    @Before
//    fun setup() {
//        IdlingRegistry.getInstance().register(IdlingResourceHelper.countingIdlingResource)
//    }

    @Test
    fun testSearchPlace() {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()

        homeScreen.search(romanovRest)
                .restaurantCellIsDisplayed(romanovRest)
                .restaurantCellIsNotDisplayed(hakkasanRest)
    }
}