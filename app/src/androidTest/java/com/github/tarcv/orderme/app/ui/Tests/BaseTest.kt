package com.github.tarcv.orderme.app.ui.Tests

import com.github.tarcv.orderme.app.ui.Screens.LoginScreen
import com.github.tarcv.orderme.app.ui.Screens.MockQRCodeScreen

open class BaseTest {

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