package com.iambedant.pizzaapp.feature.selection

import com.iambedant.pizzaapp.data.SelectableVariationGroupItems
import com.iambedant.pizzaapp.mvibase.MviViewState

/**
 * Created by @iamBedant on 15/05/18.
 */
data class PizzaSelectionViewState(
        val isLoading: Boolean,
        val groups: List<SelectableVariationGroupItems>,
        val error: Throwable?) : MviViewState {
    companion object {
        fun idle(): PizzaSelectionViewState {
            return PizzaSelectionViewState(
                    isLoading = false,
                    groups = emptyList(),
                    error = null
            )
        }
    }
}
