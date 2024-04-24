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
import io.fabric8.mockwebserver.DefaultMockServer
import readJSONFromAsset
import org.junit.After
import org.junit.Before
import org.junit.Rule
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

    @get:Rule
    var clearPreferencesRule = ClearPreferencesRule()
    @get:Rule
    var clearDatabaseRule = ClearDatabaseRule()
    @get:Rule
    var clearFilesRule = ClearFilesRule()

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
                .once()

        mockWebServer.expect()
                .post()
                .withPath("/menu/waiter")
                .andReturn(200, readJSONFromAsset("menuWaiter.json"))
                .once()

        mockWebServer.expect()
                .get()
                .withPath("/menu/3")
                .andReturn(200, readJSONFromAsset("menu.json"))
                .once()
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
                .once()
        mockWebServer.expect()
                .get()
                .withPath("/reserve")
                .andReturn(200, readJSONFromAsset("reservations.json"))
                .once()
        mockWebServer.expect()
                .post()
                .withPath("/menu/order")
                .andReturn(200, readJSONFromAsset("singleOrder.json"))
                .once()
        mockWebServer.expect()
                .post()
                .withPath("/reserve")
                .andReturn(200, readJSONFromAsset("singleReservation.json"))
                .once()
        LoginRobot().loginLater()
    }
    fun tapDeviceBackButton() {
        pressBack()
    }
}
