package com.iambedant.pizzaapp.data.remote

/**
 * Created by @iamBedant on 16/05/18.
 */
class PizzaService(private val pizzaApi: PizzaApi) {
    fun fetchPizzaDetails() = pizzaApi.getPizza()
}