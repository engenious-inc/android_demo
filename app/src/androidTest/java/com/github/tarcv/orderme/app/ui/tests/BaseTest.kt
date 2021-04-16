package com.github.tarcv.orderme.app.ui.tests

import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Utils
import com.github.tarcv.orderme.app.ui.di.AndroidTestAppComponent
import com.github.tarcv.orderme.app.ui.robots.LoginRobot
import com.github.tarcv.orderme.app.ui.robots.login
import com.github.tarcv.orderme.app.ui.robots.restaurantList
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import io.engenious.sift.ScreenshotOnFailureRule
import io.fabric8.mockwebserver.DefaultMockServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import readJSONFromAsset
import javax.inject.Inject

open class BaseTest {
    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(Utils.countingIdlingResource)
        (App.component as AndroidTestAppComponent).injectBaseTest(this)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(Utils.countingIdlingResource)
    }

    @Inject
    lateinit var mockWebServer: DefaultMockServer

    private val clearPreferencesRule = ClearPreferencesRule()
    private val clearDatabaseRule = ClearDatabaseRule()
    private val clearFilesRule = ClearFilesRule()
    private val screenshotRule = ScreenshotOnFailureRule()

    @get:Rule
    val rules: TestRule = RuleChain.emptyRuleChain()
            .around(clearDatabaseRule)
            .around(clearFilesRule)
            .around(clearPreferencesRule)
            .around(screenshotRule)

    val republiqueQR = "3_5"
    val inValidQR = "123"
    val republique = "Republique"
    val hakkasan = "Hakkasan"
    val romanov = "Romanov"
    val oceanSeafood = "Ocean Seafood"
    val colemanFarmsLittleGems: String = "COLEMAN FARMS LITTLE GEMS"
    val blackAndWhiteSalad: String = "BLACK & WHITE SALAD"
    val octopus: String = "OCTOPUS"

    fun loginLaterAndOpenMockQR() {
        login {
            loginLater()
        }

        restaurantList {
            tapOnQrCodeButton()
        }
    }

    fun setupMockData() {
        mockWebServer.expect()
                .get()
                .withPath("/places")
                .andReturn(200, readJSONFromAsset("places.json"))
                .always()

        mockWebServer.expect()
                .post()
                .withPath("/menu/waiter")
                .andReturn(200, readJSONFromAsset("menuWaiter.json"))
                .always()

        mockWebServer.expect()
                .get()
                .withPath("/menu/3")
                .andReturn(200, readJSONFromAsset("menu.json"))
                .always()
    }

    fun mockFBLogin() {
        App.sharedPreferences.edit().apply {
            putString(App.LOGIN_TOKEN, "anyTokenWillWork")
            putString(App.LOGIN_NAME, "Tester")
            putInt(App.LOGIN_ID, 10)
            putString(App.LOGIN_USER_ID, "11111111111")
        }.commit()

        mockWebServer.expect()
                .get()
                .withPath("/menu/orders")
                .andReturn(200, readJSONFromAsset("orders.json"))
                .always()
        mockWebServer.expect()
                .get()
                .withPath("/reserve")
                .andReturn(200, readJSONFromAsset("reservations.json"))
                .always()
        mockWebServer.expect()
                .post()
                .withPath("/menu/order")
                .andReturn(200, readJSONFromAsset("singleOrder.json"))
                .always()
        mockWebServer.expect()
                .post()
                .withPath("/reserve")
                .andReturn(200, readJSONFromAsset("singleReservation.json"))
                .always()
        LoginRobot().loginLater()
    }
    fun tapDeviceBackButton() {
        pressBack()
    }
}
