package com.github.tarcv.orderme.app.ui.bucket

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Bucket
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.Utils
import com.github.tarcv.orderme.app.onArrowButtonClickListener
import com.github.tarcv.orderme.app.ui.FragmentStackCloser
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.app.ui.REQUEST_CODE_QR_SCAN
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.restaurantOptions.RestaurantOptionsFragment
import com.github.tarcv.orderme.app.ui.saveOrErrorQrCodeForTable
import com.github.tarcv.orderme.app.ui.startQrCodeActivity
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Dish
import com.github.tarcv.orderme.core.data.entity.Place
import kotlinx.android.synthetic.main.fragment_bucket.*
import java.util.Calendar
import javax.inject.Inject

class BucketFragment : LifecycleLogFragment(), BucketFragmentView {

    @Inject
    lateinit var bucket: Bucket
    private lateinit var view: View
    private lateinit var dishes: MutableList<Dish>
    private lateinit var presenter: BucketPresenter
    private lateinit var countClickListener: OnCountButtonClickListener
    private lateinit var arrowListener: onArrowButtonClickListener

    private val place: Place
        get() = arguments!!.getSerializable(KEY_PLACE) as Place
    private val category: Category
        get() = arguments!!.getSerializable(KEY_CATEGORY) as Category

    interface OnCountButtonClickListener {
        fun onMinusButtonCLicked(dish: Dish)
        fun onPlusButtonCLicked(dish: Dish)
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
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        view = inflater.inflate(R.layout.fragment_bucket, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.component.inject(this)
        presenter = BucketPresenter(category.placeId)
        countClickListener = presenter
        arrowListener = parentFragment as onArrowButtonClickListener
        dishes = mutableListOf()
        fillList()
        refreshTotal()
        bucket_recycler_view.layoutManager = LinearLayoutManager(context)
        bucket_recycler_view.adapter = RestaurantOptionsAdapter(dishes, countClickListener, bucket)

        bucket_delete_all.setOnClickListener {
            presenter.deleteAll()
        }
        bucket_accept.setOnClickListener {
            presenter.accept()
        }
        back_button.setOnClickListener {
            arrowListener.onArrowClicked()
        }
    }

    private fun fillList() {
        for ((dish, count) in bucket.dishes) {
            if (count > 0)
                dishes.add(dish)
        }
    }

    override fun refreshTotal() {
        var total = 0.0
        for ((dish, count) in bucket.dishes)
            total += dish.price * count
        bucket_total.text = total.toString()
    }

    override fun getComment(): String {
        return bucket_comment.text.toString()
    }

    override fun getDate(): String {
        return Utils.getFullDate(Calendar.getInstance().timeInMillis)
    }

    override fun getSum(): Double {
        return bucket_total.text.toString().toDouble()
    }

    override fun getView(): View {
        return view
    }

    override fun detectTable() {
        this@BucketFragment.startQrCodeActivity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            saveOrErrorQrCodeForTable(context!!, place, resultCode, data)
        }
    }

    override fun onOrderAccepted() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.order_accepted_tittle)
                .setMessage(R.string.order_accepted_message)
                .setPositiveButton(R.string.ok, { dialogInterface, i ->
                    val listener = parentFragment as FragmentStackCloser
                    listener.closeUntilFragment(RestaurantOptionsFragment::class.java)
                })
                .create()
                .show()

        (parentFragment as OnOrderMadeListener).onOrderMade()
    }

    override fun onOrderError() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.error)
                .setMessage(R.string.connection_error)
                .setPositiveButton(R.string.ok, { dialogInterface, i ->
                    dialogInterface.cancel()
                })
                .create()
                .show()
    }

    override fun notifyDataSetChanged() {
        (bucket_recycler_view.adapter as RestaurantOptionsAdapter).notifyDataSetChanged()
    }

    override fun notifyNotLoggedIn() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.not_logged)
                .setMessage(R.string.should_login_order)
                .setPositiveButton(R.string.login) { dialog, _ ->
                    val intent = Intent(context, SplashActivity::class.java)
                    startActivity(intent)
                }
            .setNegativeButton(R.string.cancel) { dialogInterface, i ->
                    dialogInterface.cancel()
                }
            .create()
                .show()
    }

    companion object {
        private const val KEY_PLACE = "place"
        private const val KEY_CATEGORY = "category"

        fun create(place: Place, category: Category): BucketFragment {
            val fragment = BucketFragment()

            val arguments = Bundle()
            arguments.putSerializable(KEY_PLACE, place)
            arguments.putSerializable(KEY_CATEGORY, category)

            fragment.arguments = arguments

            return fragment
        }
    }
}

class RestaurantOptionsAdapter(
    private val dishes: List<Dish>?,
    private val countClickListener: BucketFragment.OnCountButtonClickListener,
    val bucket: Bucket
) : RecyclerView.Adapter<RestaurantOptionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOptionHolder =
            RestaurantOptionHolder(parent, viewType, bucket)

    override fun getItemCount(): Int = dishes!!.size

    override fun onBindViewHolder(holder: RestaurantOptionHolder, position: Int) {
        val dish = dishes!![position]
        holder.bind(dish)

        holder.plusButton.setOnClickListener({
            countClickListener.onPlusButtonCLicked(dish)

            var count = Integer.valueOf(holder.count.text.toString())
            count++
            holder.count.text = count.toString()
        })
        holder.minusButton.setOnClickListener({
            countClickListener.onMinusButtonCLicked(dish)

            var count = Integer.valueOf(holder.count.text.toString())
            count = Math.max(0, count - 1)
            holder.count.text = count.toString()
        })
    }
}

class RestaurantOptionHolder(parent: ViewGroup, viewType: Int, val bucket: Bucket)
    : RecyclerView.ViewHolder(createHolderItem(parent, R.layout.backet_item)) {

    val count: TextView = itemView.findViewById(R.id.menu_count) as TextView
    val name: TextView = itemView.findViewById(R.id.menu_item_name) as TextView
    val price: TextView = itemView.findViewById(R.id.menu_item_price) as TextView
    val minusButton: Button = itemView.findViewById(R.id.minus_button) as Button
    val plusButton: Button = itemView.findViewById(R.id.plus_button) as Button

    private lateinit var mDish: Dish

    fun bind(dish: Dish) {
        mDish = dish
        name.text = dish.name
        price.text = "$" + dish.price
        if (bucket.dishes[dish] != null) {
            count.text = bucket.dishes[dish].toString()
        } else {
            count.text = "0"
        }
    }

    companion object {
        fun createHolderItem(parent: ViewGroup, layoudId: Int): View =
                LayoutInflater.from(parent.context)
                        .inflate(layoudId, parent, false)
    }
}

interface OnOrderMadeListener {
    fun onOrderMade()
}