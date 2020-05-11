package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher

fun popUp(func: PopUpRobot.() -> Unit) =
        PopUpRobot().apply { func() }

class PopUpRobot : BaseRobot() {

    private val qrCodeErrorMessageMatcher: Matcher<View> = withText("QR Code could not be scanned")
    private val qrCodeSuccessMessageMatcher: Matcher<View> =
            withText("Waiter is on his way")
    private val reservationSuccessMessageMatcher: Matcher<View> =
            withText("Your reservation was made")
    private val okButtonMatcher: Matcher<View> = withText("OK")
    private val orderIsAcceptedMatcher: Matcher<View> = withText("Your order is accepted!")

    fun checkQRCodeErrorMessage() = isDisplayedBy(qrCodeErrorMessageMatcher)
    fun checkQRCodeSuccessMessage() = isDisplayedBy(qrCodeSuccessMessageMatcher)
    fun checkReservationSuccessMessage() = isDisplayedBy(reservationSuccessMessageMatcher)
    fun tapOkButton() = tapBy(okButtonMatcher)
    fun checkOrderStatus() = isDisplayedBy(orderIsAcceptedMatcher)
}
