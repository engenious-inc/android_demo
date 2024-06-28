package com.github.tarcv.orderme.app.ui

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.databinding.ActivityTabBarBinding
import com.github.tarcv.orderme.app.ui.base.BaseFragmentActivity
import com.github.tarcv.orderme.app.ui.order.OrderFragment
import com.github.tarcv.orderme.app.ui.reservation.ReservationsFragment
import com.github.tarcv.orderme.core.data.entity.Place
import timber.log.Timber

class TabBarActivity : BaseFragmentActivity() {
    private lateinit var mTabCollectionPagerAdapter: TabCollectionPagerAdapter

    private lateinit var binding: ActivityTabBarBinding
    lateinit var reservationFragment: ReservationsFragment
        private set

    lateinit var orderFragment: OrderFragment
        private set

    @get:VisibleForTesting
    lateinit var centerFragmentHolder: CenterFragmentHolder
        private set

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                binding.pager.currentItem = when (item.itemId) {
                    R.id.navigation_reservation -> 0
                    R.id.navigation_home -> 1
                    R.id.navigation_orders -> 2
                    else -> throw IllegalArgumentException()
                }
                true
            }

    private val mOnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}

        override fun onPageSelected(position: Int) {
            binding.navigation.selectedItemId = binding.navigation.menu.getItem(position).itemId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reservationFragment = ReservationsFragment()
        centerFragmentHolder = CenterFragmentHolder()
        orderFragment = OrderFragment()
        mTabCollectionPagerAdapter = TabCollectionPagerAdapter(
            supportFragmentManager, reservationFragment, centerFragmentHolder, orderFragment)
        binding.pager.adapter = mTabCollectionPagerAdapter
        binding.pager.addOnPageChangeListener(mOnPageChangeListener)
        binding.pager.offscreenPageLimit = 2

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        binding.navigation.selectedItemId = R.id.navigation_home
    }

    override fun onBackPressed() {
        if (centerFragmentHolder.getStack().size > 1) {
            Timber.i("onBackPressed")
            centerFragmentHolder.onBackPressed()
        } else {
            Timber.i("onBackPressed super method")
            super.onBackPressed()
        }
    }

    @VisibleForTesting
    fun openRestaurant(place: Place) {
        centerFragmentHolder.openRestaurant(place)
    }
}

class TabCollectionPagerAdapter(
    supportFragmentManager: FragmentManager,
    private var reservationsFragment: ReservationsFragment,
    private var centerFragmentHolder: CenterFragmentHolder,
    private var orderFragment: OrderFragment
)
    : FragmentStatePagerAdapter(supportFragmentManager) {
    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> reservationsFragment
                1 -> centerFragmentHolder
                2 -> orderFragment
                else -> throw IllegalArgumentException()
            }

    override fun getCount() = 3
}