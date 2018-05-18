package com.iambedant.pizzaapp.feature.selection

import com.iambedant.pizzaapp.data.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by @iamBedant on 15/05/18.
 */
class PizzaActionProcessorHolder @Inject constructor(private val repository: PizzaRepository) {


    fun transformerFromAction(): ObservableTransformer<PizzaSelectionAction, PizzaSelectionResult.LoadPizaVariationsResult> {
        return ObservableTransformer { action ->
            action.publish { shared ->
//                Observable.merge(
                        shared.ofType(PizzaSelectionAction.LoadPizzaAction::class.java).compose(loadPizza())
//                        shared.ofType(PizzaSelectionAction.SelectVariation::class.java).compose(selectedItem())
//                )
            }
        }
    }

//    private fun selectedItem(): ObservableTransformer<PizzaSelectionAction.SelectVariation, PizzaSelectionResult.SelectPizzaVariationResult> {
//        return ObservableTransformer { action ->
//            action.flatMap {
//                repository.loadPizza()
//                        .map { }
//                        .cast(PizzaSelectionResult.SelectPizzaVariationResult::class.java)
//                        .onErrorReturn { t -> PizzaSelectionResult.SelectPizzaVariationResult.Failure(t) }
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .startWith(PizzaSelectionResult.SelectPizzaVariationResult.InFlight)
//            }
//        }
//    }


    private fun loadPizza(): ObservableTransformer<PizzaSelectionAction.LoadPizzaAction, PizzaSelectionResult.LoadPizaVariationsResult> {
        return ObservableTransformer { action ->
            action.flatMap {
                repository.loadPizza()
                        .map(this::getSelectableVariations)
                        .map { selectableItems -> PizzaSelectionResult.LoadPizaVariationsResult.Success(selectableItems) }
                        .cast(PizzaSelectionResult.LoadPizaVariationsResult::class.java)
                        .onErrorReturn { t -> PizzaSelectionResult.LoadPizaVariationsResult.Failure(t) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .startWith(PizzaSelectionResult.LoadPizaVariationsResult.InFlight)
            }
        }
    }


    private fun getSelectableVariations(pizza: Pizza): List<SelectableVariationGroupItems> {

        /**
         * creating a hashmap for excluded items
         */
        val excludeMap: HashMap<String, String> = hashMapOf()
        for (i in pizza.variants.excludeList){
            for (j in i){
                excludeMap[j.groupId] = j.variationId
            }
        }

        val selectableVariationGroupItems = mutableListOf<SelectableVariationGroupItems>()
        for (groups in pizza.variants.variantGroups) {
            val variationList = mutableListOf<SelectableVariations>()
            for (items in groups.variations) {
                var isExcluded = false
                if(excludeMap[groups.groupId]==items.id){
                    isExcluded = true
                }
                if (items.default == 1) {
                    variationList.add(SelectableVariations(items, true, isExcluded))
                } else {
                    variationList.add(SelectableVariations(items, false, isExcluded))
                }


            }
            selectableVariationGroupItems.add(SelectableVariationGroupItems(groupId = groups.groupId,
                    variations = variationList, name = groups.name))
        }


        return selectableVariationGroupItems
    }


}