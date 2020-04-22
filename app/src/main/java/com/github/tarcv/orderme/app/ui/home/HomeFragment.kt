package com.github.tarcv.orderme.app.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.PlaceDiffCallback
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.UpdatableListAdapter
import com.github.tarcv.orderme.app.UpdatableListHolder
import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.app.ui.REQUEST_CODE_QR_SCAN
import com.github.tarcv.orderme.app.ui.saveOrErrorQrCode
import com.github.tarcv.orderme.app.ui.startQrCodeActivity
import com.github.tarcv.orderme.app.wireToAdapter
import com.github.tarcv.orderme.core.data.entity.Place
import com.jakewharton.rxbinding2.widget.queryTextChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@MainThread
class HomeFragment : LifecycleLogFragment(), HomeView {
    @Inject
    lateinit var presenter: HomePresenter

    val myCountingIdlingResource = CountingIdlingResource("myCountingIdlingResource")

    // TODO: consider removing onStop
    private val placesListAdapter: PlacesListAdapter by lazy {
        PlacesListAdapter()
    }

    private val disposer = CompositeDisposable()

    override fun onStart() {
        super.onStart()

        val searchTextChanges = searchView.queryTextChanges()
        presenter.bind(this, searchTextChanges)
    }

    override fun wirePlacesSource(source: Observable<List<Place>>) {
        disposer.add(source
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { IdlingResourceHelper.countingIdlingResource.decrement() }
                .wireToAdapter(
                        placesListAdapter,
                        BiFunction { current, next -> PlaceDiffCallback(current, next) })
        )
    }

    override fun onStop() {
        presenter.unbind()
        disposer.clear()
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        App.component.injectHomeFragment(this)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantRecycler.requestFocus()
        placesListAdapter.restaurantClickListener = parentFragment as OnRestaurantClickListener
        searchBtn.setOnClickListener({
            this@HomeFragment.startQrCodeActivity()
        })

        restaurantRecycler.itemAnimator = DefaultItemAnimator()
        restaurantRecycler.adapter = placesListAdapter
        restaurantRecycler.layoutManager = LinearLayoutManager(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            saveOrErrorQrCode(context!!, presenter, resultCode, data)?.let { (place, _) ->
                (parentFragment as OnRestaurantClickListener).onRestaurantClicked(place!!)
            }
        }
    }
}

class PlacesListAdapter : UpdatableListAdapter<Place, PlacesListHolder>() {
    lateinit var restaurantClickListener: OnRestaurantClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PlacesListHolder(parent)

    override fun onBindViewHolder(holder: PlacesListHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val place = getBoundItemAt(position)
        holder.itemView.setOnClickListener({
            restaurantClickListener.onRestaurantClicked(place)
        })
    }
}

class PlacesListHolder(parent: ViewGroup)
    : UpdatableListHolder<Place>(createHolderItem(parent, R.layout.restaurant_card)) {

    val image: SimpleDraweeView = itemView.findViewById(R.id.titleImage)
    val title: TextView = itemView.findViewById(R.id.titleText)
    private val address: TextView = itemView.findViewById(R.id.titleAddress)

    private lateinit var place: Place

    override fun bind(item: Place) {
        this.place = item
        title.text = item.name
        address.text = item.address
        image.setImageURI(item.imagePath)
    }

    companion object {
        fun createHolderItem(parent: ViewGroup, layoutId: Int): View =
                LayoutInflater.from(parent.context)
                        .inflate(layoutId, parent, false)
    }
}

interface OnRestaurantClickListener {
    fun onRestaurantClicked(place: Place)
}
