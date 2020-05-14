package com.github.tarcv.orderme.app.ui.robots

import android.view.View
import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.PickerActions.setDate
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.github.tarcv.orderme.app.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.equalTo

fun reservation(func: ReservationRobot.() -> Unit) = ReservationRobot().apply { func() }

class ReservationRobot : BaseRobot() {
    private val bookButtonMatcher: Matcher<View> = withId(R.id.make_reservation_book_button)
    private val numberOfPeopleMatcher: Matcher<View> = withId(R.id.make_reservation_people)
    private val phoneNumberMatcher: Matcher<View> = withId(R.id.make_reservation_phone)

    fun tapBookButton() = tapBy(bookButtonMatcher)
    fun typeNumberOfPeople(numberOfPeople: String) =
            typeInText(numberOfPeople, numberOfPeopleMatcher)

    fun typePhoneNumber(phoneNumber: String) = typeInText(phoneNumber, phoneNumberMatcher)

    fun selectDate(date: String) {
        val newYear = date.substring(0, 4).toInt()
        val newMonth = date.substring(5, 7).toInt()
        val newDay = date.substring(8, 10).toInt()
        onView(withClassName(equalTo(DatePicker::class.java.name)))
                .perform(setDate(newYear, newMonth, newDay))
    }
}
