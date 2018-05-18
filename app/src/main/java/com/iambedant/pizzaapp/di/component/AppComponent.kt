package com.iambedant.pizzaapp.di.component

import com.iambedant.pizzaapp.PizzaApp
import com.iambedant.pizzaapp.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by @iamBedant on 17/05/18.
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    ActivityBindingModule::class
])
interface AppComponent : AndroidInjector<PizzaApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PizzaApp>() {

        abstract fun appModule(appModule: AppModule): Builder

        override fun seedInstance(instance: PizzaApp?) {
            appModule(AppModule(instance!!))
        }
    }

}