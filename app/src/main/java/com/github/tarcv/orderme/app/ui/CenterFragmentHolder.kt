package com.github.tarcv.orderme.app.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.Utils
import com.github.tarcv.orderme.app.di.IdlingResourceHelper
import com.github.tarcv.orderme.app.onArrowButtonClickListener
import com.github.tarcv.orderme.app.ui.bucket.BucketFragment
import com.github.tarcv.orderme.app.ui.bucket.OnOrderMadeListener
import com.github.tarcv.orderme.app.ui.home.HomeFragment
import com.github.tarcv.orderme.app.ui.home.OnRestaurantClickListener
import com.github.tarcv.orderme.app.ui.makeReservation.MakeReservationFragment
import com.github.tarcv.orderme.app.ui.makeReservation.OnReservationMadeListener
import com.github.tarcv.orderme.app.ui.menu.RestaurantMenuFragment
import com.github.tarcv.orderme.app.ui.menuCategory.RestaurantMenuCategories
import com.github.tarcv.orderme.app.ui.restaurantOptions.RestaurantOptionsFragment
import com.github.tarcv.orderme.app.ui.restaurantOptions.RestaurantOptionsFragment.Companion.KEY_PLACE
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.entity.Category
import com.github.tarcv.orderme.core.data.entity.Dish
import com.github.tarcv.orderme.core.data.entity.Place
import com.github.tarcv.orderme.core.data.request.CallWaiterRequest
import com.github.tarcv.orderme.core.data.response.CallWaiterResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.Calendar
import java.util.Deque
import java.util.LinkedList
import javax.inject.Inject

class CenterFragmentHolder : LifecycleLogFragment(), OnRestaurantClickListener,
        RestaurantOptionsFragment.OnButtonClickListener,
        RestaurantMenuCategories.OnPlaceCategoryClickListener,
        RestaurantMenuFragment.OnInfoButtonCLickListener,
        RestaurantMenuFragment.OnPlaceBucketCLickListener,
        onArrowButtonClickListener,
        OnReservationMadeListener,
        OnOrderMadeListener,
        FragmentStackCloser {

    private lateinit var mStack: Deque<Fragment>
    private lateinit var parentView: View
    @Inject
    lateinit var apiClient: ApiClient

    fun getStack() = mStack

    override fun onCategoryClick(place: Place, category: Category) {
        val restaurantMenuFragment = RestaurantMenuFragment.create(place, category)

        pushFragment(restaurantMenuFragment)
    }

    @VisibleForTesting
    fun pushFragment(fragment: Fragment) {
        mStack.addLast(fragment)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
                .replace(R.id.center_fragment_holder_framelayout, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onMenuCLicked(place: Place) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_PLACE, place)

        val restaurantMenuCategories = RestaurantMenuCategories()
        restaurantMenuCategories.arguments = bundle

        pushFragment(restaurantMenuCategories)
    }

    override fun onReservationClicked(place: Place) {
        val makeReservationFragment = MakeReservationFragment()
        makeReservationFragment.place = place

        pushFragment(makeReservationFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        parentView = inflater.inflate(R.layout.center_fragment_holder, container, false)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mStack = LinkedList<Fragment>()
        putHomeFragment()
        App.component.inject(this)
    }

    private fun putHomeFragment() {
        val homeFragment = HomeFragment()

        mStack.addLast(homeFragment)

        childFragmentManager.beginTransaction()
                .add(R.id.center_fragment_holder_framelayout, homeFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onRestaurantClicked(place: Place) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_PLACE, place)

        val restaurantOptionFragment = RestaurantOptionsFragment()
        restaurantOptionFragment.arguments = bundle

        pushFragment(restaurantOptionFragment)
    }

    override fun onArrowClicked() {
        onBackPressed()
    }

    override fun closeUntilFragment(fragmentClass: Class<out Fragment>) {
        val presentInStack = mStack.any { fragmentClass.isInstance(it) }
        if (presentInStack) {
            while (!fragmentClass.isInstance(mStack.last)) {
                onBackPressed()
            }
        }
    }

    fun onBackPressed() {
        mStack.removeLast()
        val fragment = mStack.last
        childFragmentManager.beginTransaction()
                .remove(
                    childFragmentManager.findFragmentById(R.id.center_fragment_holder_framelayout))
                .add(R.id.center_fragment_holder_framelayout, fragment)
                .commit()
    }

    override fun callWaiter(place: Place, table: Int) {
        IdlingResourceHelper.countingIdlingResource.increment()
        val created = Utils.getFullDate(Calendar.getInstance().timeInMillis)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.reason_dialog_tittle)
                .setItems(R.array.reasons, DialogInterface.OnClickListener { _, which ->
                    callAWaiterRequest(place.id, table, created, which)
                })
                .create()
                .show()
        IdlingResourceHelper.countingIdlingResource.decrement()
    }

    override fun onPhoneClicked(place: Place) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + place.phone)
        startActivity(intent)
    }

    override fun onLocationClicked(place: Place) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:" + place.latitude + "," + place.longitude)
        startActivity(intent)
    }

    override fun onBucketClicked(place: Place, category: Category) {
        val bucketFragment = BucketFragment.create(place, category)

        pushFragment(bucketFragment)
    }

    override fun onInfoClick(dish: Dish) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.dish_description)
                .setMessage(dish.description)
                .setPositiveButton(R.string.ok, { dialogInterface, i -> dialogInterface.cancel() })
                .create()
                .show()
    }

    fun showReasonDialog(): Int {
        var res = -1
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.reason_dialog_tittle)
                .setItems(R.array.reasons, { dialog, which -> res = which })
                .create()
                .show()
        return res
    }

    fun callAWaiterRequest(placeId: Int, tableId: Int, created: String, reason: Int) {
        IdlingResourceHelper.countingIdlingResource.increment()
        apiClient.callWaiter(CallWaiterRequest(placeId, tableId, created, reason))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext,
                        this::onError)
    }

    private fun onNext(response: CallWaiterResponse) {
        Timber.i("onNext")
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.success)
                .setMessage(R.string.waiter_response)
                .setPositiveButton(R.string.ok, { dialogInterface, i -> dialogInterface.cancel() })
                .create()
                .show()
        IdlingResourceHelper.countingIdlingResource.decrement()
    }

    private fun onError(throwable: Throwable) {
        Timber.i("onError")
        throwable.printStackTrace()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.error)
                .setMessage(R.string.connection_error)
                .setNegativeButton(R.string.ok) { dialog, _ -> dialog.cancel() }
                .create()
                .show()
        IdlingResourceHelper.countingIdlingResource.decrement()
    }

    override fun onReservationMade() {
        (activity as TabBarActivity).reservationFragment.presenter.updateReservations()
    }

    override fun onOrderMade() {
        (activity as TabBarActivity).orderFragment.presenter.getOrders()
    }

    @VisibleForTesting
    fun openRestaurant(place: Place) {
        onRestaurantClicked(place)
    }
}

interface FragmentStackCloser {
    fun closeUntilFragment(fragmentClass: Class<out Fragment>)
}