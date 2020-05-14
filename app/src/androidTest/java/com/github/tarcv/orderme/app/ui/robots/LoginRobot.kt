package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.tarcv.orderme.app.R
import getJsonValue
import org.hamcrest.Matcher

fun login(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }

class LoginRobot : BaseRobot() {
    private val loginLaterButtonMatcher: Matcher<View> = withId(R.id.login_later_button)
    private val continueButtonMatcher: Matcher<View> = withText("Continue")
    private val facebookButtonMatcher: Matcher<View> = withId(R.id.login_button)
    private val emailField: String = "m_login_email"
    private val passwordField: String = "m_login_password"
    private val loginButton: String = "login"
    private val confirmationButton: String = "__CONFIRM__"
    private val facebookEmail = getJsonValue("email")
    private val facebookPassword = getJsonValue("password")

    fun loginLater() = tapBy(loginLaterButtonMatcher)
    fun checkContinueButtonPresent() = isDisplayedBy(continueButtonMatcher)
}
