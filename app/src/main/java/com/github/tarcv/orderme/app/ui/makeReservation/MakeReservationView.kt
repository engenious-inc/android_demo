package com.github.tarcv.orderme.app.ui.makeReservation

/**
 * Created by vladimirshykun on 3/15/18.
 */
interface MakeReservationView {
    fun notifyReservationMade()
    fun getPhoneNumber(): String
    fun getPeopleCount(): Int
    fun notifyReservationError()
    fun notifyNotLoggedIn()
    fun notifyReservationIncomplete()
}