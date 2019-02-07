package com.github.tarcv.orderme.app.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.ui.Screens.HomeScreen
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.MockQRCodeScreen
import com.github.tarcv.orderme.app.ui.Screens.RestaurantScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QRCodeTests {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    val tableID = "3_5"

    @Test
    fun qrCodeFromList() {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.clickOnLoginLaterButton()

        val qrCodeScreen = homeScreen.clickOnQRCodeButton()

        qrCodeScreen.enter(tableID)
                .clickOnSubmitButton()

        val restaurantScreen = RestaurantScreen()
        restaurantScreen.optionsAreDisplayed
    }
}