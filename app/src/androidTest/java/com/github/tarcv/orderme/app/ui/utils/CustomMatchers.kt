// ktlint-disable filename
package com.github.tarcv.orderme.app.ui.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class RecyclerViewMatcher private constructor(private val count: Int)
    : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("RecyclerView has $count items ")
    }

    override fun matchesSafely(item: RecyclerView?): Boolean {
        return item?.adapter?.itemCount == count
    }

    companion object {
        fun recyclerElementCount(count: Int): RecyclerViewMatcher = RecyclerViewMatcher(count)
    }
}
