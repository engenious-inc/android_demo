package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.github.tarcv.orderme.app.R
import org.hamcrest.Matcher

fun qrCode(func: QrRobot.() -> Unit) = QrRobot().apply { func() }

class QrRobot : BaseRobot() {
    private val simulateErrorButtonMatcher: Matcher<View> = withId(R.id.errorButton)
    private val qrCodeTextFieldMatcher: Matcher<View> = withId(R.id.qrCodeText)
    private val submitButtonMatcher: Matcher<View> = withId(R.id.submitButton)
    private val errorPopUpMatcher: Matcher<View> = withText("Scan Error")

    fun typeInNewQrCode(qrCode: String) = typeInText(qrCode, qrCodeTextFieldMatcher)
    fun simulateError() = tapBy(simulateErrorButtonMatcher)
    fun tapOnSubmitButton() = tapBy(submitButtonMatcher)
    fun errorPopUpIsDispalyed() = isDisplayedBy(errorPopUpMatcher)
}
