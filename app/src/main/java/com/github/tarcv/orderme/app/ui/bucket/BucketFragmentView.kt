package com.github.tarcv.orderme.app.ui.bucket

import android.view.View

interface BucketFragmentView {
    fun refreshTotal()
    fun getComment(): String
    fun getDate(): String
    fun getSum(): Double
    fun detectTable()
    fun getView(): View
    fun onOrderAccepted()
    fun onOrderError()
    fun notifyDataSetChanged()
    fun notifyNotLoggedIn()
}