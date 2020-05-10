package com.github.tarcv.orderme.app.ui.restaurantOptions

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.onArrowButtonClickListener
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.app.ui.REQUEST_CODE_QR_SCAN
import com.github.tarcv.orderme.app.ui.saveOrErrorQrCodeForTable
import com.github.tarcv.orderme.app.ui.startQrCodeActivity
import com.github.tarcv.orderme.core.data.entity.Place
import kotlinx.android.synthetic.main.fragment_restaurant_options.*
import timber.log.Timber

class RestaurantOptionsFragment : LifecycleLogFragment() {

    companion object {
        const val DETECT_TABLE = "Detect table"
        const val MENU = "Menu"
        const val RESERVATION = "Reservation"
        const val CALL_A_WAITER = "Call a waiter"
        const val PHONE = "Phone"
        const val LOCATION = "Location"
        const val KEY_PLACE = "place"
    }

    interface OnButtonClickListener {
        fun onMenuCLicked(place: Place)
        fun onReservationClicked(place: Place)
        fun callWaiter(place: Place, table: Int)
        fun onPhoneClicked(place: Place)
        fun onLocationClicked(place: Place)
    }

    val place: Place
        get() = arguments?.getSerializable(KEY_PLACE) as Place

    private val numberOfColumns: Int = 3
    private lateinit var buttonClickListener: OnButtonClickListener
    private lateinit var arrowListener: onArrowButtonClickListener
    private lateinit var options: List<Option>
    private lateinit var parentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        initList()
        val parentView = inflater.inflate(R.layout.fragment_restaurant_options, container, false)
        return parentView
    }

    private fun initList() {
        val context = context
        if (context != null) {
            options = listOf(
                    Option(
                        getString(R.string.detect_table),
                        context.resources.getDrawable(R.drawable.qr_code, null)
                    ),
                    Option(
                        getString(R.string.menu),
                        context.resources.getDrawable(R.drawable.menu, null)
                    ),
                    Option(
                        getString(R.string.reservation),
                        context.resources.getDrawable(R.drawable.reservation, null)
                    ),
                    Option(
                        getString(R.string.call_a_waiter),
                        context.resources.getDrawable(R.drawable.call_waiters, null)
                    ),
                    Option(
                        getString(R.string.phone),
                        context.resources.getDrawable(R.drawable.phone, null)
                    ),
                    Option(
                        getString(R.string.location),
                        context.resources.getDrawable(R.drawable.map_location, null)
                    )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonClickListener = parentFragment as OnButtonClickListener
        arrowListener = parentFragment as onArrowButtonClickListener
        restaurant_options_recycler.layoutManager =
            object : GridLayoutManager(context, numberOfColumns) {
                override fun canScrollVertically() = false
            }
        val detectTable = {
            this@RestaurantOptionsFragment.startQrCodeActivity()
        }
        restaurant_options_recycler.adapter =
            RestaurantOptionsAdapter(options, buttonClickListener, detectTable, place)

        restaurant_name.text = place.name
        (restaurant_image_view as ImageView).setImageURI(Uri.parse(place.imagePath))
        back_button.setOnClickListener {
            arrowListener.onArrowClicked()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            saveOrErrorQrCodeForTable(context!!, place, resultCode, data)
        }
    }
}

class RestaurantOptionsAdapter(
    private val options: List<Option>,
    private val buttonClickListener: RestaurantOptionsFragment.OnButtonClickListener,
    private val detectTable: () -> Unit,
    val place: Place
)
    : RecyclerView.Adapter<RestaurantOptionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOptionHolder {
        Timber.i("onCreateViewHolder")
        return RestaurantOptionHolder(parent, viewType, buttonClickListener, detectTable, place)
    }

    override fun onBindViewHolder(holder: RestaurantOptionHolder, position: Int) {
        val option = options[position]
        holder.bind(option)
    }

    override fun getItemCount(): Int = options.size
}

class RestaurantOptionHolder(
    val parent: ViewGroup,
    viewType: Int,
    private val buttonCLickListener: RestaurantOptionsFragment.OnButtonClickListener,
    private val detectTable: () -> Unit,
    val place: Place
)
    : RecyclerView.ViewHolder(createHolderItem(parent, R.layout.option_card)) {

    val image: ImageView = itemView.findViewById(R.id.item_card_image_view) as ImageView
    val name: TextView = itemView.findViewById(R.id.item_card_text_view) as TextView

    private lateinit var mOption: Option

    fun bind(option: Option) {
        mOption = option
        name.text = option.name
        image.setImageDrawable(option.drawable)
        itemView.setOnClickListener {
            when (option.name) {
                RestaurantOptionsFragment.DETECT_TABLE -> {
                    detectTable.invoke()
                }
                RestaurantOptionsFragment.MENU -> buttonCLickListener.onMenuCLicked(place)
                RestaurantOptionsFragment.RESERVATION -> {
                    buttonCLickListener.onReservationClicked(place)
                }
                RestaurantOptionsFragment.CALL_A_WAITER -> {
                    val table = App.tryGetTable()
                    if (table == null || table.place != place.id) {
                        Snackbar.make(parent, "Detect your table, please", Snackbar.LENGTH_LONG)
                            .setAction("Detect") {
                                detectTable.invoke()
                            }.show()
                    } else {
                        buttonCLickListener.callWaiter(place, table.tableNumber)
                    }
                }
                RestaurantOptionsFragment.PHONE -> buttonCLickListener.onPhoneClicked(place)
                RestaurantOptionsFragment.LOCATION -> buttonCLickListener.onLocationClicked(place)
            }
        }
    }

    companion object {
        fun createHolderItem(parent: ViewGroup, layoutId: Int): View =
                LayoutInflater.from(parent.context)
                        .inflate(layoutId, parent, false)
    }
}

data class Option(val name: String, val drawable: Drawable)
