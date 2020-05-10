package com.github.tarcv.orderme.app.ui.Tests

import androidx.test.espresso.IdlingRegistry
import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.MockQRCodeScreen
import org.junit.After
import org.junit.Before

open class BaseTest {

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(IdlingResourceHelper.countingIdlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(IdlingResourceHelper.countingIdlingResource)
    }

    val republiqueQRCode = "3_5"
    val republique = "Republique"
    val romanovRest: String = "Romanov"
    val hakkasanRest: String = "Hakkasan"

    fun loginLaterAndNavigateToMockQR(): MockQRCodeScreen {
        val loginScreen = LoginScreen()
        val homeScreen = loginScreen.loginLater()
        return homeScreen.search()
    }
}