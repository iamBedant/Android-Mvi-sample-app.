package com.iambedant.pizzaapp.data

import com.iambedant.pizzaapp.data.remote.PizzaService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by @iamBedant on 15/05/18.
 */
class PizzaRepositoryImpl @Inject constructor(private val service: PizzaService) : PizzaRepository {
    override fun loadPizza(): Observable<Pizza> {
        return service.fetchPizzaDetails()
    }
}