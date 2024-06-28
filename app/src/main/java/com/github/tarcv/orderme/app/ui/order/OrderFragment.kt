package com.github.tarcv.orderme.app.ui.order

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.login.LoginManager
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.PlacesRepository
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.Utils
import com.github.tarcv.orderme.app.databinding.OrderBinding
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.core.data.entity.Order
import javax.inject.Inject

class OrderFragment : LifecycleLogFragment(), OrderView {

    private lateinit var orders: List<Order>
    private lateinit var binding: OrderBinding

    @Inject
    lateinit var presenter: OrderPresenter

    @Inject
    lateinit var placesRepository: PlacesRepository

    override fun setOrders(orders: List<Order>) {
        this.orders = orders
        binding.orderRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.orderRecyclerview.adapter = OrderAdapter(orders, placesRepository)
    }

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
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        App.component.injectOrderFragment(this)
        binding = OrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ApplySharedPref")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setActionBar(binding.orderToolbar)
        activity?.actionBar?.title = ""

        binding.logoutButton.setOnClickListener {
            val fragment = this@OrderFragment

            if (App.sharedPreferences.getString(App.LOGIN_TOKEN, "") == "") {
                Snackbar.make(
                    fragment.requireView(),
                    R.string.not_logged,
                    Snackbar.LENGTH_LONG
                ).show()
            } else {

                val builder = AlertDialog.Builder(fragment.getFragmentContext())
                builder.setTitle(R.string.logout)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            LoginManager.getInstance().logOut()
                            App.sharedPreferences.edit().apply {
                                putString(App.LOGIN_TOKEN, "")
                                putString(App.LOGIN_NAME, "")
                                putInt(App.LOGIN_ID, -1)
                                putString(App.LOGIN_USER_ID, "")
                            }.commit()
                            fragment.requireActivity().finishAfterTransition()
                        }
                    .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                    .create()
                        .show()
            }
        }
    }

    override fun getFragmentContext(): Context? {
        return context
    }
}

class OrderAdapter(
    var data: List<Order>?,
    val placesRepository: PlacesRepository
) : RecyclerView.Adapter<OrderAdapter.VHOrder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.VHOrder {
        return VHOrder(
            LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderAdapter.VHOrder, position: Int) {
        val order = getItem(position)
        holder.restaurant.text = placesRepository.getNameById(order.placeId)
        holder.date.text = Utils.getDateFromFullDate(order.created)
        holder.time.text = Utils.getTimeFromFullDate(order.created)
        holder.check.text = "$${order.sum}"
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    private fun getItem(position: Int): Order {
        return data!![position]
    }

    class VHOrder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var restaurant: TextView = itemView.findViewById(R.id.order_restaurant) as TextView
        var date: TextView = itemView.findViewById(R.id.order_date) as TextView
        var time: TextView = itemView.findViewById(R.id.order_time) as TextView
        var check: TextView = itemView.findViewById(R.id.order_check) as TextView
    }
}
