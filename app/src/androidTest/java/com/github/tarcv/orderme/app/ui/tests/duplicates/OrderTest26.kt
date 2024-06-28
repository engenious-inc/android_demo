package com.github.tarcv.orderme.app.ui.tests.duplicates

import com.github.tarcv.orderme.app.ui.tests.BaseTest

import androidx.test.rule.ActivityTestRule
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.robots.restaurantList
import com.github.tarcv.orderme.app.ui.robots.restaurant
import com.github.tarcv.orderme.app.ui.robots.qrCode
import com.github.tarcv.orderme.app.ui.robots.menuItem
import com.github.tarcv.orderme.app.ui.robots.menuCategories
import com.github.tarcv.orderme.app.ui.robots.bucket
import com.github.tarcv.orderme.app.ui.robots.orders
import com.github.tarcv.orderme.app.ui.robots.login
import com.github.tarcv.orderme.app.ui.robots.reservation
import com.github.tarcv.orderme.app.ui.robots.reservations
import com.github.tarcv.orderme.app.ui.robots.popUp
import org.junit.Rule

class OrderTest26 : BaseTest() {
    @get:Rule
    val mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    private val comment = "San Jose"
    private val phoneNumber = "1111111"
    private val numberOfPeople = "4"
    private val orderDate = "2020-04-27"
    private val time = "02:04"
    private val bucketTotal = "77.0"
    private val reservationDate = "2020-06-27"
    private val restaurantName = "Republique"

    fun completeOrderFlowForRepublique() {
        setupMockData()

        Thread.sleep(5000L)
        login {
            mockFBLogin()
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
            tapOnMenuOption()
        }

        Thread.sleep(5000L)
        menuCategories {
            tapSaladsAndVegetables()
        }

        Thread.sleep(5000L)
        menuItem {
            tapOnPlusButton(colemanFarmsLittleGems)
            tapOnPlusButton(blackAndWhiteSalad)
            tapOnPlusButton(octopus)
            tapOnShoppingCart()
        }

        Thread.sleep(5000L)
        bucket {
            typeInComments(comment)
            tapOnAccept()
        }

        Thread.sleep(5000L)
        popUp {
            checkOrderStatus()
            tapOkButton()
        }

        Thread.sleep(5000L)
        restaurant {
            tapOnOrders()
        }

        Thread.sleep(5000L)
        orders {
            checkOrderIsDisplayed(restaurantName, orderDate, time, bucketTotal)
        }
    }

    fun restaurantReservationFlow() {
        setupMockData()
        Thread.sleep(5000L)
        login {
            mockFBLogin()
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
            tapOnReservation()
        }

        Thread.sleep(5000L)
        reservation {
            typeNumberOfPeople(numberOfPeople)
            typePhoneNumber(phoneNumber)
            selectDate(reservationDate)
            tapBookButton()
        }

        Thread.sleep(5000L)
        popUp {
            checkReservationSuccessMessage()
            tapOkButton()
        }

        Thread.sleep(5000L)
        reservations {
            tapOnReservations()
            tapFutureReservations()
            isReservationDisplayed(restaurantName, reservationDate, time)
        }
    }
}
