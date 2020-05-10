package com.github.tarcv.orderme.app.ui.reservation

import android.os.Bundle
import androidx.annotation.MainThread
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.ListItemDiffCallback
import com.github.tarcv.orderme.app.PlacesRepository
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.UpdatableListAdapter
import com.github.tarcv.orderme.app.UpdatableListHolder
import com.github.tarcv.orderme.app.Utils
import com.github.tarcv.orderme.app.ui.LifecycleLogFragment
import com.github.tarcv.orderme.app.wireToAdapter
import com.github.tarcv.orderme.core.data.entity.Reserve
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.reservation.*
import javax.inject.Inject
import kotlin.math.roundToInt

@MainThread
class ReservationsFragment : LifecycleLogFragment(), ReservationView {
    @Inject
    lateinit var placesRepository: PlacesRepository

    @Inject
    lateinit var presenter: ReservationsPresenter

    private val futureReservationsAdapter: ReservationAdapter by lazy {
        ReservationAdapter(placesRepository)
    }
    private val historyReservationsAdapter: ReservationAdapter by lazy {
        ReservationAdapter(placesRepository)
    }

    private val disposer = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        App.component.inject(this)
        return inflater.inflate(R.layout.reservation, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun wireReservationsSource(source: Observable<List<Reserve>>) {
        val sharedReserveSource = source.observeOn(AndroidSchedulers.mainThread()).share()
        disposer.add(sharedReserveSource
                .map {
                    it.filter { reserve -> Utils.laterThanNow(reserve.date) }
                }
                .wireToAdapter(
                        futureReservationsAdapter,
                        BiFunction { current, next -> ReserveDiffCallback(current, next) })
        )
        disposer.add(sharedReserveSource
                .map {
                    it.filter { reserve -> !Utils.laterThanNow(reserve.date) }
                }
                .wireToAdapter(
                        historyReservationsAdapter,
                        BiFunction { current, next -> ReserveDiffCallback(current, next) })
        )
    }

    override fun onStop() {
        presenter.unbind()
        disposer.clear()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reservation_recycler.adapter = historyReservationsAdapter
        setupTabs()
    }

    private fun setupTabs() {
        tab_layout.addTab(tab_layout.newTab().setText(R.string.history_reservations), true)
        tab_layout.addTab(tab_layout.newTab().setText(R.string.future_reservations))
        tab_layout.setTabMarginsAndBack(0)
        tab_layout.setTabMarginsAndBack(1)

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateRecyclerForTab()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        updateRecyclerForTab()
    }

    @Synchronized
    private fun updateRecyclerForTab() {
        reservation_recycler.adapter = if (tab_layout.selectedTabPosition == 0) {
            historyReservationsAdapter
        } else {
            futureReservationsAdapter
        }
        reservation_recycler.layoutManager = LinearLayoutManager(context)
    }
}

class ReservationAdapter(val placesRepository: PlacesRepository)
    : UpdatableListAdapter<Reserve, ReserveItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ReserveItemHolder(placesRepository, parent)

    override fun onBindViewHolder(holder: ReserveItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val reserve = getBoundItemAt(position)
        holder.bind(reserve)
    }
}

class ReserveItemHolder(val placesRepository: PlacesRepository, parent: ViewGroup)
    : UpdatableListHolder<Reserve>(createHolderItem(parent, R.layout.reservation_item)) {
    var restaurant: TextView = itemView.findViewById(R.id.reservation_restaurant) as TextView
    var date: TextView = itemView.findViewById(R.id.reservation_date) as TextView
    var time: TextView = itemView.findViewById(R.id.reservation_time) as TextView

    override fun bind(item: Reserve) {
        restaurant.text = placesRepository.getNameById(item.placeId)
        date.text = Utils.getDateFromFullDate(item.date)
        time.text = Utils.getTimeFromFullDate(item.date)
    }

    companion object {
        fun createHolderItem(parent: ViewGroup, layoutId: Int): View =
                LayoutInflater.from(parent.context)
                        .inflate(layoutId, parent, false)
    }
}

fun TabLayout.setTabMarginsAndBack(tabPosition: Int) {
    val displayMetrics = context?.resources?.displayMetrics
    val density = displayMetrics?.density ?: 1.0f

    val layout = (this.getChildAt(0) as LinearLayout).getChildAt(tabPosition) as LinearLayout
    layout.setBackgroundResource(
            when (tabPosition) {
                0 -> R.drawable.tab_left
                else -> R.drawable.tab_right
            })
    val layoutParams = layout.layoutParams as LinearLayout.LayoutParams

    when (tabPosition) {
        0 -> layoutParams.marginStart = (16.0f * density).roundToInt()
        1 -> layoutParams.marginEnd = (16.0f * density).roundToInt()
    }

    layout.layoutParams = layoutParams
}

class ReserveDiffCallback(
    current: List<Reserve>,
    next: List<Reserve>
) : ListItemDiffCallback<Reserve>(current, next) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areContentsTheSame(oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            current[oldItemPosition] == next[newItemPosition]
}