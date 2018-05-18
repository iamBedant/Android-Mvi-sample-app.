package com.iambedant.pizzaapp.di.module

import com.iambedant.pizzaapp.feature.selection.PizzaSelectionActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by @iamBedant on 16/05/18.
 */
@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [(PizzaSelectionModule::class)])
    abstract fun pizzaSelectionActivity(): PizzaSelectionActivity
}