package com.github.tarcv.orderme.app.ui.makeReservation

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.onArrowButtonClickListener
import com.github.tarcv.orderme.app.ui.FragmentStackCloser
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.restaurantOptions.RestaurantOptionsFragment
import com.github.tarcv.orderme.core.data.entity.Place
import kotlinx.android.synthetic.main.make_reservation.*
import java.util.Calendar

class MakeReservationFragment : MakeReservationView, LifecycleLogFragment() {

    private lateinit var presenter: MakeReservationPresenter

    lateinit var place: Place
    private lateinit var arrowListener: onArrowButtonClickListener

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.make_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MakeReservationPresenter(place)
        arrowListener = parentFragment as onArrowButtonClickListener

        make_reservation_image_view.setImageURI(place.imagePath)

        make_reservation_time_picker.setOnTimeChangedListener { _, hourOfDay, minute ->
            presenter.timeChanged(hourOfDay, minute)
        }

        make_reservation_date_picker
                .init(
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                ) { _, year, month, dayOfMonth ->
                    presenter.dateChanged(year, month, dayOfMonth)
                }

        make_reservation_book_button.setOnClickListener {
            bookButtonClicked()
        }

        back_button.setOnClickListener {
            arrowListener.onArrowClicked()
        }
    }

    private fun bookButtonClicked() {
        presenter.book()
    }

    override fun notifyReservationMade() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.success)
                .setMessage(R.string.reservation_success)
                .setPositiveButton(R.string.ok) { dialogInterface, _ ->
                    val listener = parentFragment as FragmentStackCloser
                    listener.closeUntilFragment(RestaurantOptionsFragment::class.java)
                }
                .create()
                .show()

        (parentFragment as OnReservationMadeListener).onReservationMade()
    }

    override fun notifyReservationError() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.error)
                .setMessage(R.string.connection_error)
                .setNegativeButton(R.string.ok) { dialog, _ -> dialog.cancel() }
                .create()
                .show()
    }

    override fun notifyReservationIncomplete() {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.apply {
            setTitle("Reservation error")
            setMessage("Verify that all fields are filled correctly")
            setButton(AlertDialog.BUTTON_NEUTRAL, "OK", { dialog, _ -> dialog.dismiss() })
            show()
        }
    }

    override fun getPhoneNumber(): String {
        return make_reservation_phone.text.toString()
    }

    override fun getPeopleCount(): Int {
        return Integer.parseInt(make_reservation_people.text.toString())
    }

    override fun notifyNotLoggedIn() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.not_logged)
                .setMessage(R.string.should_login_reservation)
                .setPositiveButton(R.string.login) { _, _ ->
                    val intent = Intent(context, SplashActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .create()
                .show()
    }
}

interface OnReservationMadeListener {
    fun onReservationMade()
}