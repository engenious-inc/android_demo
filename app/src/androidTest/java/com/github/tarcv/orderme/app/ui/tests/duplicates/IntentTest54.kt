package com.github.tarcv.orderme.app.ui.tests.duplicates

import com.github.tarcv.orderme.app.ui.tests.BaseTest

import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_VIEW
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.login
import com.github.tarcv.orderme.app.ui.robots.qrCode
import com.github.tarcv.orderme.app.ui.robots.restaurant
import com.github.tarcv.orderme.app.ui.robots.restaurantList
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class IntentTest54 : BaseTest() {
    @get:Rule
    val intentsTestRule = IntentsTestRule(SplashActivity::class.java)

    private val longRepublique = 34.064198
    private val latRepublique = -118.343863
    private val hakkasanPhoneNumber = "+1 415-829-8148"
    private val romanovPhoneNumber = "+1 818 760-3177"
    private val republiquePhoneNumber = "+1 310-362-6115"
    private val oceanSeafoodPhoneNumber = "+1 213-687-3088"

    @Before
    fun blockExternalApps() {
        intending(not(isInternal()))
                .respondWith(ActivityResult(RESULT_OK, null))
    }

    @Test(timeout = 1*60*1000L)
    fun testOpenMap() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            tapOnQrCodeButton()
        }

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnMap()
        }

        intended(
                allOf(
                        hasAction(equalTo(ACTION_VIEW)),
                        hasData("geo:$longRepublique,$latRepublique")
                )
        )
    }

    @Test(timeout = 1*60*1000L)
    fun testOpenDialerForRomanov() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(romanov)
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnPhone()
        }

        intended(
                allOf(
                        hasAction(equalTo(ACTION_DIAL)),
                        hasData("tel:$romanovPhoneNumber")
                )
        )
    }

    @Test(timeout = 1*60*1000L)
    fun testOpenDialerForRepublique() {
        setupMockData()
        Thread.sleep(5000L)
        loginLaterAndOpenMockQR()

        Thread.sleep(5000L)
        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnPhone()
        }

        intended(
                allOf(
                        hasAction(equalTo(ACTION_DIAL)),
                        hasData("tel:$republiquePhoneNumber")
                )
        )
    }

    @Test(timeout = 1*60*1000L)
    fun testOpenDialerForHakkasan() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(hakkasan)
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnPhone()
        }

        intended(
                allOf(
                        hasAction(equalTo(ACTION_DIAL)),
                        hasData("tel:$hakkasanPhoneNumber")
                )
        )
    }

    @Test(timeout = 1*60*1000L)
    fun testOpenDialerForOceanSeafood() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            loginLater()
        }

        Thread.sleep(5000L)
        restaurantList {
            selectRestaurant(oceanSeafood)
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnPhone()
        }

        intended(
                allOf(
                        hasAction(equalTo(ACTION_DIAL)),
                        hasData("tel:$oceanSeafoodPhoneNumber")
                )
        )
    }
}
