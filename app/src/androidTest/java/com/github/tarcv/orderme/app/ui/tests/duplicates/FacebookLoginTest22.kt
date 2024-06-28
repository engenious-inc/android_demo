package com.github.tarcv.orderme.app.ui.tests.duplicates

import com.github.tarcv.orderme.app.ui.tests.BaseTest

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import org.junit.Rule
import org.junit.Test

class FacebookLoginTest22 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test(timeout = 1*60*1000L)
    fun testFacebookLogin() {
        login {
            mockFBLogin()
            Thread.sleep(5000L)
            tapDeviceBackButton()
            Thread.sleep(5000L)
            checkContinueButtonPresent()
        }
    }
}
