package com.iambedant.pizzaapp.feature.selection

import com.iambedant.pizzaapp.data.SelectVariation
import com.iambedant.pizzaapp.mvibase.MviIntent


/**
 * Created by @iamBedant on 15/05/18.
 */
sealed class PizzaSelectionIntent : MviIntent {
    object InitialIntent : PizzaSelectionIntent()
    data class SelectGroupVariationsIntent(val groupVariations: SelectVariation) : PizzaSelectionIntent()
}