package com.iambedant.pizzaapp.data

import io.reactivex.Observable
import io.reactivex.Single


/**
 * Created by @iamBedant on 15/05/18.
 */
interface PizzaRepository {
    fun loadPizza(): Observable<Pizza>
}