package com.github.tarcv.orderme.core

import com.github.tarcv.orderme.core.data.entity.Order
import com.github.tarcv.orderme.core.data.entity.Place
import com.github.tarcv.orderme.core.data.entity.Reserve
import com.github.tarcv.orderme.core.data.request.CallWaiterRequest
import com.github.tarcv.orderme.core.data.request.MakeOrderRequest
import com.github.tarcv.orderme.core.data.request.MakeReservationRequest
import com.github.tarcv.orderme.core.data.response.CallWaiterResponse
import com.github.tarcv.orderme.core.data.response.DeletePlaceResponse
import com.github.tarcv.orderme.core.data.response.DeleteReservationResponse
import com.github.tarcv.orderme.core.data.response.GetMenuResponse
import com.github.tarcv.orderme.core.data.response.LoginResponse
import com.github.tarcv.orderme.core.data.response.MakeOrderResponse
import com.github.tarcv.orderme.core.data.response.MakeReservationResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/places")
    fun places(): Single<List<Place>>

    @POST("/menu/waiter")
    fun callWaiter(@Body request: CallWaiterRequest): Observable<CallWaiterResponse>

    @GET("/menu/{placeId}")
    fun getMenu(@Path("placeId") placeId: Int): Observable<GetMenuResponse>

    @POST("/menu/order")
    fun makeOrder(@Body request: MakeOrderRequest): Observable<MakeOrderResponse>

    @POST("/reserve")
    fun makeReservation(@Body request: MakeReservationRequest): Observable<MakeReservationResponse>

    @GET("/user")
    fun login(@Query("access_token") accessToken: String): Observable<LoginResponse>

    @GET("/reserve")
    fun getReservations(): Observable<List<Reserve>>

    @DELETE("/places/reservations/{id}")
    fun deleteReservation(@Path("id") id: Int): Observable<DeleteReservationResponse>

    @GET("/menu/orders")
    fun getOrders(): Observable<List<Order>>

    // For Testing
    @POST("/places")
    fun addPlace(@Body request: Place): Observable<Place>

    // For Testing
    @DELETE("/places")
    fun deletePlace(placeId: Int): Observable<DeletePlaceResponse>
}

/*
    static func deletePlace(id: Int, completion: @escaping (_ success: Bool?, _ error : NSError?) -> () ) {
        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }


            completion(true, nil)
        }

        send(api: "/places\(id)", method: .delete, parameters: nil, token: "", completion: response_completion)

    }

}
    static func callAWaiter( placeId : Int, idTable: Int, date: Date, reason: Int, completion: @escaping (_ success: Bool? , _ error: NSError?) -> () )  {

        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }
            completion(true, nil)
        }

        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        let created = dateFormatter.string(from: date)


        let parameters = [
            "place_id" : placeId,
            "idtable" : idTable,
            "created" : created,
            "reason" : reason

            ] as [String : Any]

        send(api: "/menu/waiter", method: .post, parameters: parameters, token: "", completion: response_completion)

    }


data class GetMenuResponse(
        val categories: List<Category>,
        val dishes: List<Dish>
) {
    fun withDishes(newDishes: List<Dish>) = GetMenuResponse(categories, newDishes)
}

data class Category(
        val id: Int,
        val placeId: Int,
        val name: String
)

data class Dish(
        var id: Int,
        var category: Category,
        var name: String,
        var price: Double,
        var dishDescription: String,
        var category_id: Int
) {
    fun withCategory(newCategory: Category): Dish =
            Dish(id, newCategory, name, price, dishDescription, newCategory.id)
}


static func getMenu(placeId: Int, completion: @escaping (_ menu: Menu?, _ error : NSError?) -> () ) {

    func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
        if response_error != nil {
            completion(nil, response_error)
            return
        }

        guard let menuJson = response_result else {
        completion(nil, NSError())
        return
    }
        let menu : Menu? = Mapper<Menu>().map(JSONString: menuJson)

        guard let categories = menu?.categories,
        let dishes = menu?.dishes  else {
            completion(nil, NSError())
            return
        }

        var newDishes : [Dish] = []
        for dish in dishes {

            let categoryId = dish.category_id

                    for category in categories {
                        if categoryId == category.id {
                            dish.category = category
                            newDishes.append(dish)
                        }
                    }
        }
        menu?.dishes = newDishes
        completion(menu, nil)

    }

    send(api: "/menu/\(placeId)", method: .get, parameters: nil, token: "", completion: response_completion )

}
// make a Reservation
static func makeReservation(reserve: Reserve, completion: @escaping (_ successId: Int?, _ error : NSError?) -> () ) {

    func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
        if response_error != nil {
            completion(nil, response_error)
            return
        }
        if let jsonReserve = response_result  {
            if let reserve = Reserve(JSONString: jsonReserve) {
                if let id = reserve.id  {
                    completion(id, nil)
                    return
                }
            }
        }
        completion(nil, NSError())
        return
    }

    // make String Dates
    guard let dateForReservation = reserve.date,
        let id = reserve.place?.id,
        let phoneNumber = reserve.phoneNumber,
        let people = reserve.numberOfPeople else {
            completion(nil, NSError())
            return
    }

    dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    let date = dateFormatter.string(from : dateForReservation)
    let created = dateFormatter.string(from: Date())


    let parameters = ["place_id" : id,
                      "date"  : date,
                      "created" : created,
                      "phonenumber" : phoneNumber,
                      "numberofpeople" : people
        ] as [String : Any]

    guard let token = SingleTone.shareInstance.user?.token else {
        completion(nil, NSError())
        return
    }
    send(api: "/places/reserve", method: .post, parameters: parameters, token: token, completion: response_completion)
}

    // Login to API after facebook registration
    static func login(accessToken: String, completion: @escaping (_ user: User?, _ error : NSError?) -> () ) {

        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }
            guard let json = response_result else {
                completion(nil, NSError())
                return
            }
            let userOpt : User? = Mapper<User>().map(JSONString: json)
            guard let user = userOpt else {
                completion(nil, NSError())
                return
            }
            completion(user, nil)
        }

        send(api: "/user?access_token=\(accessToken)", method: .get, parameters: nil, token: "", completion: response_completion )
    }
    // Get all Reservations
    static func getReservations(completion: @escaping (_ reservations: [Reserve]?, _ error : NSError?) -> () ) {

        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }
            guard let json = response_result else {
                completion(nil, NSError())
                return
            }
            let reserves : [Reserve]? = Mapper<Reserve>().mapArray(JSONString: json)

            completion(reserves, nil)
        }


        guard let token = SingleTone.shareInstance.user?.token else {
            completion(nil, NSError())
            return
        }
        send(api: "/places/reservations", method: .get, parameters: nil, token: token, completion: response_completion )
    }

    // Delete reservation
    static func deleteReservation(id: Int, completion: @escaping (_ success: Bool?, _ error : NSError?) -> () ) {

        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }
            completion(true, nil)
        }

        guard let token = SingleTone.shareInstance.user?.token else {
            completion(nil, NSError())
            return
        }
        send(api: "/places/reservations/\(id)", method: .delete, parameters: nil, token: token, completion: response_completion )
    }


    // Get all Orders
    static func getOrders(completion: @escaping (_ orders: [Order]?, _ error : NSError?) -> () ) {

        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }
            guard let json = response_result else {
                completion(nil, NSError())
                return
            }
            let orders : [Order]? = Mapper<Order>().mapArray(JSONString: json)

            completion(orders, nil)
        }


        guard let token = SingleTone.shareInstance.user?.token else {
            completion(nil, NSError())
            return
        }

        var newDishes : [Dish] = []
        for dish in dishes {

            let categoryId = dish.category_id

                    for category in categories {
                        if categoryId == category.id {
                            dish.category = category
                            newDishes.append(dish)
                        }
                    }
        }
        menu?.dishes = newDishes
        completion(menu, nil)


    // For Testing

    static func addPlace(placeJson: [String: AnyObject], completion: @escaping (_ place: Place?, _ error : NSError?) -> () ) {
        func response_completion( _ response_result: String? , response_error: NSError? ) -> Void {
            if response_error != nil {
                completion(nil, response_error)
                return
            }
            guard let json = response_result else {
                completion(nil, NSError())
                return
            }
            let place :Place? = Mapper<Place>().map(JSONString: json)

            completion(place, nil)
        }

        send(api: "/places", method: .post, parameters: placeJson, token: "", completion: response_completion)

    }
*/