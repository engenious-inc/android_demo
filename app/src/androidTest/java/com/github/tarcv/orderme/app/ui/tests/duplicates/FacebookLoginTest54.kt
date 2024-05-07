package com.github.tarcv.orderme.app.ui.tests.duplicates

import com.github.tarcv.orderme.app.ui.tests.BaseTest
import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import org.junit.Rule
import org.junit.Test


class FacebookLoginTest54 : BaseTest() {
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
