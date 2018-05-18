package com.iambedant.pizzaapp

import com.iambedant.pizzaapp.di.component.DaggerAppComponent
import com.iambedant.pizzaapp.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by @iamBedant on 17/05/18.
 */
class PizzaApp : DaggerApplication(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        return component!!    }

}