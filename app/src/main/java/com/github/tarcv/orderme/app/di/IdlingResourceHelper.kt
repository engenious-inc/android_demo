package com.github.tarcv.orderme.app.di

import android.support.test.espresso.idling.CountingIdlingResource

object IdlingResourceHelper {

    val countingIdlingResource = CountingIdlingResource("CountingIdlingResource")
}