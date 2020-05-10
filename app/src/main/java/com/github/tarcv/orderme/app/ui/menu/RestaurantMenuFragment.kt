package com.github.tarcv.orderme.app.ui.menu

import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Bucket
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.onArrowButtonClickListener
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Dish
import com.github.tarcv.orderme.core.data.entity.Place
import kotlinx.android.synthetic.main.menu.back_button
import kotlinx.android.synthetic.main.menu.bucket_button
import kotlinx.android.synthetic.main.menu.bucket_textview
import kotlinx.android.synthetic.main.menu.category_name
import kotlinx.android.synthetic.main.menu.menu_image_view
import kotlinx.android.synthetic.main.menu.menu_recycler
import java.lang.Math.max
import javax.inject.Inject

class RestaurantMenuFragment : LifecycleLogFragment(), RestaurantMenuView {

    interface OnInfoButtonCLickListener {
        fun onInfoClick(dish: Dish)
    }

    interface OnCountButtonClickListener {
        fun onMinusButtonCLicked(dish: Dish)
        fun onPlusButtonCLicked(dish: Dish)
    }

    interface OnBucketCLickListener {
        fun onBucketClicked(category: Category)
    }

    interface OnPlaceBucketCLickListener {
        fun onBucketClicked(place: Place, category: Category)
    }

    val numberOfColumns: Int = 3

    @Inject
    lateinit var bucket: Bucket

    private val place: Place
        get() = arguments!!.getSerializable(KEY_PLACE) as Place
    private val category: Category
        get() = arguments!!.getSerializable(KEY_CATEGORY) as Category

    lateinit var countClickListener: OnCountButtonClickListener
    lateinit var infoCLickListener: OnInfoButtonCLickListener
    lateinit var bucketClickListener: OnPlaceBucketCLickListener
    lateinit var presenter: RestaurantMenuPresenter
    lateinit var arrowListener: onArrowButtonClickListener

    private lateinit var dishes: List<Dish>

    override fun setDishes(dishes: List<Dish>) {
        this.dishes = dishes
        menu_recycler.adapter =
            RestaurantOptionsAdapter(dishes, infoCLickListener, countClickListener, bucket)
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
        App.component.inject(this)
        presenter = RestaurantMenuPresenter(category)
        return inflater.inflate(R.layout.menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (menu_image_view as ImageView).setImageURI(Uri.parse(place.imagePath))
        category_name.text = category.name

        arrowListener = parentFragment as onArrowButtonClickListener
        infoCLickListener = parentFragment as OnInfoButtonCLickListener
        bucketClickListener = parentFragment as OnPlaceBucketCLickListener
        countClickListener = presenter
        bucket_button.setOnClickListener(View.OnClickListener {
            bucketClickListener.onBucketClicked(place, category)
        })
        back_button.setOnClickListener(View.OnClickListener {
            arrowListener.onArrowClicked()
        })
        menu_recycler.layoutManager = LinearLayoutManager(context)
        refreshSum()
    }

    override fun refreshSum() {
        var sum = 0.0
        for ((dish, count) in bucket.dishes) {
            sum += dish.price * count
        }
        bucket_textview.text = sum.toString()
    }

    companion object {
        private const val KEY_PLACE = "place"
        private const val KEY_CATEGORY = "category"

        fun create(place: Place, category: Category): RestaurantMenuFragment {
            val fragment = RestaurantMenuFragment()

            val arguments = Bundle()
            arguments.putSerializable(KEY_PLACE, place)
            arguments.putSerializable(KEY_CATEGORY, category)

            fragment.arguments = arguments

            return fragment
        }
    }
}

class RestaurantOptionsAdapter(
    private val dishes: List<Dish>,
    private val infoClickListener: RestaurantMenuFragment.OnInfoButtonCLickListener,
    private val countClickListener: RestaurantMenuFragment.OnCountButtonClickListener,
    val bucket: Bucket
)
    : RecyclerView.Adapter<RestaurantOptionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOptionHolder =
            RestaurantOptionHolder(parent, viewType, bucket)

    override fun getItemCount(): Int = dishes.size

    override fun onBindViewHolder(holder: RestaurantOptionHolder, position: Int) {
        val dish = dishes[position]
        holder.bind(dish)

        holder.infoButton.setOnClickListener({
            infoClickListener.onInfoClick(dish)
        })
        holder.plusButton.setOnClickListener({
            countClickListener.onPlusButtonCLicked(dish)

            var count = Integer.valueOf(holder.count.text.toString())
            count++
            holder.count.text = count.toString()
        })
        holder.minusButton.setOnClickListener({
            countClickListener.onMinusButtonCLicked(dish)

            var count = Integer.valueOf(holder.count.text.toString())
            count = max(0, count - 1)
            holder.count.text = count.toString()
        })
    }
}

class RestaurantOptionHolder(parent: ViewGroup, viewType: Int, val bucket: Bucket)
    : RecyclerView.ViewHolder(createHolderItem(parent, R.layout.menu_item)) {

    val count: TextView = itemView.findViewById(R.id.menu_count) as TextView
    val name: TextView = itemView.findViewById(R.id.menu_item_name) as TextView
    val price: TextView = itemView.findViewById(R.id.menu_item_price) as TextView
    val infoButton: Button = itemView.findViewById(R.id.info_button) as Button
    val minusButton: Button = itemView.findViewById(R.id.minus_button) as Button
    val plusButton: Button = itemView.findViewById(R.id.plus_button) as Button

    private lateinit var mDish: Dish

    fun bind(dish: Dish) {
        mDish = dish
        name.text = dish.name
        price.text = "$ ${dish.price}"
        if (bucket.dishes[dish] != null) {
            count.text = bucket.dishes[dish].toString()
        } else {
            count.text = "0"
        }
    }

    companion object {
        fun createHolderItem(parent: ViewGroup, layoutId: Int): View =
                LayoutInflater.from(parent.context)
                        .inflate(layoutId, parent, false)
    }
}
