package com.github.tarcv.orderme.app.ui.tests

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import org.junit.Rule
import org.junit.Test

class NewFeatureTest60 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun testFacebookLogin() {
        login {
            mockFBLogin()
            tapDeviceBackButton()
            checkContinueButtonPresent()
        }
    }
}