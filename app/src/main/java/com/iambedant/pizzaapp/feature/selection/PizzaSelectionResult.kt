package com.iambedant.pizzaapp.feature.selection

import com.iambedant.pizzaapp.data.SelectVariation
import com.iambedant.pizzaapp.data.SelectableVariationGroupItems
import com.iambedant.pizzaapp.mvibase.MviResult

/**
 * Created by @iamBedant on 16/05/18.
 */
sealed class PizzaSelectionResult : MviResult {
    sealed class LoadPizaVariationsResult : PizzaSelectionResult() {
        data class Success(val variations: List<SelectableVariationGroupItems>) : LoadPizaVariationsResult()
        data class Failure(val error: Throwable) : LoadPizaVariationsResult()
        object InFlight : LoadPizaVariationsResult()

    }

    sealed class SelectPizzaVariationResult : PizzaSelectionResult() {
        data class Success(val selectedVariation: SelectVariation) : SelectPizzaVariationResult()
        data class Failure(val error: Throwable) : SelectPizzaVariationResult()
        object InFlight : SelectPizzaVariationResult()
    }
}