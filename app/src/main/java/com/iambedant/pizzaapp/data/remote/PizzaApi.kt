package com.iambedant.pizzaapp.data.remote

import com.iambedant.pizzaapp.data.Pizza
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by @iamBedant on 15/05/18.
 */
interface PizzaApi {

    companion object {
        val BASE_URL = "https://api.myjson.com/"
    }

    @GET("bins/3b0u2")
    fun getPizza(): Observable<Pizza>

}