package com.github.tarcv.orderme.app.di

import android.content.Context
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.Bucket
import com.github.tarcv.orderme.app.MainScheduler
import com.github.tarcv.orderme.app.PlacesRepository
import com.github.tarcv.orderme.app.ui.home.HomeFragment
import com.github.tarcv.orderme.app.ui.home.HomePresenter
import com.github.tarcv.orderme.app.ui.order.OrderFragment
import com.github.tarcv.orderme.app.ui.order.OrderPresenter
import com.github.tarcv.orderme.app.ui.reservation.ReservationsPresenter
import com.github.tarcv.orderme.core.ApiClient
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesContext(): Context = context

    @Provides
    @Singleton
    fun providesPlacesRepository(client: ApiClient): PlacesRepository = PlacesRepository(client)

    @Provides
    @Singleton
    fun providesHomePresenter(
        placesRepository: PlacesRepository,
        @MainScheduler mainScheduler: Scheduler
    ): HomePresenter = HomePresenter(placesRepository, mainScheduler)

    @Provides
    @Singleton
    fun providesApiClient(@BaseUrl baseUrl: String) = ApiClient(
        App.sharedPreferences.getString(App.LOGIN_TOKEN, "") ?: "",
        baseUrl
    )

    @Provides
    @Singleton
    fun providesReservationPresenter(
        apiClient: ApiClient
    ): ReservationsPresenter = ReservationsPresenter(apiClient)

    @Provides
    @Singleton
    fun providesOrderPresenter() = OrderPresenter()

    @Provides
    fun providesOrderFragment() = OrderFragment()

    @Provides
    fun providesHomeFragment() = HomeFragment()

    @Provides
    @Singleton
    fun providesBucket() = Bucket()

    @Provides
    @MainScheduler
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()
}