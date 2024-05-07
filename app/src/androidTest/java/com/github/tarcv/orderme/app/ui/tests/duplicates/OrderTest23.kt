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

class OrderTest23 : BaseTest() {
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
        login {
            mockFBLogin()
        }

        restaurantList {
            tapOnQrCodeButton()
        }

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        restaurant {
            tapOnMenuOption()
        }

        menuCategories {
            tapSaladsAndVegetables()
        }

        menuItem {
            tapOnPlusButton(colemanFarmsLittleGems)
            tapOnPlusButton(blackAndWhiteSalad)
            tapOnPlusButton(octopus)
            tapOnShoppingCart()
        }

        bucket {
            typeInComments(comment)
            tapOnAccept()
        }

        popUp {
            checkOrderStatus()
            tapOkButton()
        }

        restaurant {
            tapOnOrders()
        }

        orders {
            checkOrderIsDisplayed(restaurantName, orderDate, time, bucketTotal)
        }
    }

    fun restaurantReservationFlow() {
        setupMockData()
        login {
            mockFBLogin()
        }

        restaurantList {
            tapOnQrCodeButton()
        }

        qrCode {
            typeInNewQrCode(republiqueQR)
            tapOnSubmitButton()
        }

        restaurant {
            tapOnReservation()
        }

        reservation {
            typeNumberOfPeople(numberOfPeople)
            typePhoneNumber(phoneNumber)
            selectDate(reservationDate)
            tapBookButton()
        }

        popUp {
            checkReservationSuccessMessage()
            tapOkButton()
        }

        reservations {
            tapOnReservations()
            tapFutureReservations()
            isReservationDisplayed(restaurantName, reservationDate, time)
        }
    }
}
