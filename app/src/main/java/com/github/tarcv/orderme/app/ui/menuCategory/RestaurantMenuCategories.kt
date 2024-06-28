package com.github.tarcv.orderme.app.ui.menuCategory

import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.databinding.CategoriesFragmentLayoutBinding
import com.github.tarcv.orderme.app.onArrowButtonClickListener
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.app.ui.restaurantOptions.RestaurantOptionsFragment
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Place
import timber.log.Timber

class RestaurantMenuCategories : LifecycleLogFragment(), RestaurantMenuCategoriesView {

    interface OnPlaceCategoryClickListener {
        fun onCategoryClick(place: Place, category: Category)
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    private val place: Place
        get() = arguments?.getSerializable(RestaurantOptionsFragment.KEY_PLACE) as Place

    private val numberOfColumns: Int = 3
    private lateinit var presenter: RestaurantMenuCategoriesPresenter
    private lateinit var categories: List<Category>
    private lateinit var buttonCLickListener: OnPlaceCategoryClickListener
    private lateinit var arrowListener: onArrowButtonClickListener
    private lateinit var binding: CategoriesFragmentLayoutBinding

    override fun setCategories(categories: List<Category>) {
        Timber.i("setCategories")
        this.categories = categories
        binding.categoriesRecycler.adapter = RestaurantCategoriesAdapter(
            categories,
            object : OnCategoryClickListener {
                override fun onCategoryClick(category: Category) {
                    buttonCLickListener.onCategoryClick(place, category)
                }
            }
        )
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

        presenter = RestaurantMenuCategoriesPresenter(place)
        binding = CategoriesFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonCLickListener = parentFragment as OnPlaceCategoryClickListener
        arrowListener = parentFragment as onArrowButtonClickListener
        binding.categoriesRecycler.layoutManager = GridLayoutManager(context, numberOfColumns)
        (binding.restaurantImageView as ImageView).setImageURI(Uri.parse(place.imagePath))
        binding.backButton.setOnClickListener {
            arrowListener.onArrowClicked()
        }
    }
}

class RestaurantCategoriesAdapter(
    private val categories: List<Category>?,
    private val buttonClickListener: RestaurantMenuCategories.OnCategoryClickListener
)
    : RecyclerView.Adapter<RestaurantOptionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RestaurantOptionHolder(parent, viewType)

    override fun getItemCount(): Int {
        return categories!!.size
    }

    override fun onBindViewHolder(holder: RestaurantOptionHolder, position: Int) {
        val category = categories!![position]
        holder.bind(category)
        holder.itemView.setOnClickListener({
            buttonClickListener.onCategoryClick(category)
        })
    }
}

class RestaurantOptionHolder(parent: ViewGroup, viewType: Int)
    : RecyclerView.ViewHolder(createHolderItem(parent, R.layout.menu_card)) {

    val name: TextView = itemView.findViewById(R.id.item_card_text_view) as TextView

    private lateinit var mCategory: Category

    fun bind(category: Category) {
        mCategory = category
        name.text = category.name
    }

    companion object {
        fun createHolderItem(parent: ViewGroup, layoutId: Int): View =
                LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }
}
