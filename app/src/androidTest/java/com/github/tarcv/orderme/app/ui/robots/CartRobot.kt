package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R
import org.hamcrest.Matcher

fun bucket(func: CartRobot.() -> Unit) = CartRobot().apply { func() }

class CartRobot : BaseRobot() {
    private val cartTotalValueMatcher: Matcher<View> = withId(R.id.bucket_textview)
    private val commentMatcher: Matcher<View> = withId(R.id.bucket_comment)
    private val acceptButtonMatcher: Matcher<View> = withId(R.id.bucket_accept)
    private val bucketTotalMatcher: Matcher<View> = withId(R.id.bucket_total)

    fun getBucketTotalOrder() = getMatcherText(bucketTotalMatcher).toDouble()
    fun getBucketTotal() = getMatcherText(cartTotalValueMatcher).toDouble()
    fun typeInComments(comment: String) = typeInText(comment, commentMatcher)
    fun tapOnAccept() = tapBy(acceptButtonMatcher)
}
