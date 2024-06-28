package com.github.tarcv.orderme.core

import com.github.tarcv.orderme.core.data.entity.Order
import com.github.tarcv.orderme.core.data.entity.Place
import com.github.tarcv.orderme.core.data.entity.Reserve
import com.github.tarcv.orderme.core.data.entity.User
import com.github.tarcv.orderme.core.data.request.CallWaiterRequest
import com.github.tarcv.orderme.core.data.request.MakeOrderRequest
import com.github.tarcv.orderme.core.data.request.MakeReservationRequest
import com.github.tarcv.orderme.core.data.response.DeleteReservationResponse
import com.github.tarcv.orderme.core.data.response.GetMenuResponse
import com.github.tarcv.orderme.core.data.response.MakeOrderResponse
import com.github.tarcv.orderme.core.data.response.MakeReservationResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * TODO: consider implementing these methods as data classes serializer/deserializers
 */
open class ApiClient(private var token: String, baseUrl: String) {
    private val apiService: ApiService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    val newRequest = chain.request()
                            .newBuilder()
                            .header("Authorization", "Token " + token)
                            .build()

                    chain.proceed(newRequest)
                }
                .addInterceptor(logging)
                .build()

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun getPlaces(): Single<List<Place>> = apiService.places()

    fun getMenu(placeId: Int): Observable<GetMenuResponse> = apiService.getMenu(placeId)

    fun makeOrder(request: MakeOrderRequest): Observable<MakeOrderResponse> {
        return apiService.makeOrder(request)
    }

    fun makeReservation(reserve: Reserve): Observable<MakeReservationResponse> {
        return apiService.makeReservation(MakeReservationRequest(reserve))
    }

    fun login(accessToken: String) = apiService.login(accessToken)

    fun getReservations(): Observable<List<Reserve>> {
        return apiService.getReservations()
    }

    fun getOrders(): Observable<List<Order>> {
        return apiService.getOrders()
    }

    fun deleteReservation(id: Int): Observable<DeleteReservationResponse> {
        return apiService.deleteReservation(id)
    }

    fun callWaiter(callWaiterRequest: CallWaiterRequest) =
            apiService.callWaiter(callWaiterRequest)

    fun addPlace(place: Place) = apiService.addPlace(place)

    fun deletePlace(placeId: Int) = apiService.deletePlace(placeId)
}

object Singleton {
    var place: Place? = null
    var tableID: Int = -1
    var user: User? = null
}