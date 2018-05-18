package com.iambedant.pizzaapp.feature.selection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by @iamBedant on 16/05/18.
 */
data class PizzaSelectionViewmodelFactory @Inject constructor(
        private val pizzaActionProcessorHolder: PizzaActionProcessorHolder
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PizzaSelectionViewModel(pizzaActionProcessorHolder) as T
    }
}