package com.github.tarcv.orderme.app.di

import com.github.tarcv.orderme.app.ui.CenterFragmentHolder
import com.github.tarcv.orderme.app.ui.SplashActivity
import com.github.tarcv.orderme.app.ui.bucket.BucketFragment
import com.github.tarcv.orderme.app.ui.bucket.BucketPresenter
import com.github.tarcv.orderme.app.ui.home.HomeFragment
import com.github.tarcv.orderme.app.ui.home.HomePresenter
import com.github.tarcv.orderme.app.ui.makeReservation.MakeReservationPresenter
import com.github.tarcv.orderme.app.ui.menu.RestaurantMenuFragment
import com.github.tarcv.orderme.app.ui.menu.RestaurantMenuPresenter
import com.github.tarcv.orderme.app.ui.menuCategory.RestaurantMenuCategoriesPresenter
import com.github.tarcv.orderme.app.ui.order.OrderFragment
import com.github.tarcv.orderme.app.ui.order.OrderPresenter
import com.github.tarcv.orderme.app.ui.reservation.ReservationsFragment
import com.github.tarcv.orderme.app.ui.reservation.ReservationsPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ProdModule::class])
@Singleton
interface AppComponent {
    //    fun inject(fragment: HomeView)
    fun injectOrderFragment(fragment: OrderFragment)
    fun injectReservationPresenter(presenter: ReservationsPresenter)
    fun injectOrderPresenter(presenter: OrderPresenter)
    fun injectHomeFragment(fragment: HomeFragment)
    fun injectHomePresenter(presenter: HomePresenter)
    fun injectRestaurantMenuCategoriesPresenter(presenter: RestaurantMenuCategoriesPresenter)
    fun injectRestaurantMenuPresenter(presenter: RestaurantMenuPresenter)
    fun inject(splashActivity: SplashActivity)
    fun inject(makeReservationPresenter: MakeReservationPresenter)
    fun inject(bucketPresenter: BucketPresenter)
    fun inject(bucketFragment: BucketFragment)
    fun inject(restaurantMenuFragment: RestaurantMenuFragment)
    fun inject(centerFragmentHolder: CenterFragmentHolder)
    fun inject(reservationsFragment: ReservationsFragment)
}

// Boris Hw test
