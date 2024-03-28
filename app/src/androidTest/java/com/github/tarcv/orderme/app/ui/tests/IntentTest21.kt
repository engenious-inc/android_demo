
package com.github.tarcv.orderme.app.ui.tests

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
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class IntentTest21 : BaseTest() {
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
    @Ignore
    @Test
    fun testOpenMap() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            tapOnQrCodeButton()
        }

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

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

    @Test
    fun testOpenDialerForRomanov() {
        setupMockData()
        login {
            loginLater()
        }
        restaurantList {
            selectRestaurant(romanov)
        }

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
    @Ignore
    @Test
    fun testOpenDialerForRepublique() {
        setupMockData()
        loginLaterAndOpenMockQR()

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

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

    @Test
    fun testOpenDialerForHakkasan() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            selectRestaurant(hakkasan)
        }

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

    @Test
    fun testOpenDialerForOceanSeafood() {
        setupMockData()
        login {
            loginLater()
        }

        restaurantList {
            selectRestaurant(oceanSeafood)
        }

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
