package com.iambedant.pizzaapp.di.module

import com.iambedant.pizzaapp.di.ActivityScope
import com.iambedant.pizzaapp.feature.selection.PizzaActionProcessorHolder
import com.iambedant.pizzaapp.feature.selection.PizzaSelectionViewmodelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by @iamBedant on 16/05/18.
 */
@Module(includes = [DataModule::class])
class PizzaSelectionModule {

    @Provides
    @ActivityScope
    fun providePizzaViewmodelFactory(actionProcessorHolder: PizzaActionProcessorHolder): PizzaSelectionViewmodelFactory {
        return PizzaSelectionViewmodelFactory(actionProcessorHolder)
    }


}