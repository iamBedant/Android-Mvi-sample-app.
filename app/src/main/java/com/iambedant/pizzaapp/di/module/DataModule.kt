package com.iambedant.pizzaapp.di.module

import com.iambedant.pizzaapp.data.PizzaRepository
import com.iambedant.pizzaapp.data.PizzaRepositoryImpl
import com.iambedant.pizzaapp.data.remote.PizzaService
import com.iambedant.pizzaapp.di.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by @iamBedant on 17/05/18.
 */

@Module
class DataModule {

    @Provides
    fun providePizzaRepository(service: PizzaService): PizzaRepository {
        return PizzaRepositoryImpl(service)
    }
}