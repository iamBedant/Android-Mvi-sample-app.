package com.iambedant.pizzaapp.feature.selection

import com.iambedant.pizzaapp.mvibase.MviAction

/**
 * Created by @iamBedant on 16/05/18.
 */
sealed class PizzaSelectionAction : MviAction {
    object LoadPizzaAction : PizzaSelectionAction()
    data class SelectVariation(val selectionGroup: com.iambedant.pizzaapp.data.SelectVariation) : PizzaSelectionAction()
}