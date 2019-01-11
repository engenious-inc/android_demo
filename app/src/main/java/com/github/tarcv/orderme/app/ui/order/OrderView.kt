package com.github.tarcv.orderme.app.ui.order

import android.content.Context
import com.github.tarcv.orderme.core.data.entity.Order

interface OrderView {
    fun setOrders(orders: List<Order>)
    fun getFragmentContext(): Context?
}