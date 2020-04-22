package com.github.tarcv.orderme.app.di

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResourceHelper {

    val countingIdlingResource = CountingIdlingResource("CountingIdlingResource")
}