package com.iambedant.pizzaapp.feature.selection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.iambedant.pizzaapp.data.SelectableVariationGroupItems
import com.iambedant.pizzaapp.data.SelectableVariations
import com.iambedant.pizzaapp.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

/**
 * Created by @iamBedant on 15/05/18.
 */
class PizzaSelectionViewModel(private val pizzaActionProcessorHolder: PizzaActionProcessorHolder)
    : ViewModel(), MviViewModel<PizzaSelectionIntent, PizzaSelectionViewState> {

    private val intentsSubject: PublishSubject<PizzaSelectionIntent> = PublishSubject.create()
    private val statesObservable: Observable<PizzaSelectionViewState> = compose()
    private val statesLiveData: MutableLiveData<PizzaSelectionViewState> = MutableLiveData()
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val intentFilter: ObservableTransformer<PizzaSelectionIntent, PizzaSelectionIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<PizzaSelectionIntent>(
                        shared.ofType(PizzaSelectionIntent.InitialIntent::class.java).take(1),
                        shared.filter { it != PizzaSelectionIntent.InitialIntent }
                )
            }
        }

    init {
        disposables.add(statesObservable.subscribe { statesLiveData.value = it })
    }

    override fun processIntents(intents: Observable<PizzaSelectionIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): LiveData<PizzaSelectionViewState> {
        return statesLiveData
    }


    private fun actionFromIntent(intent: PizzaSelectionIntent): PizzaSelectionAction {
        return when (intent) {
            is PizzaSelectionIntent.InitialIntent -> PizzaSelectionAction.LoadPizzaAction
            is PizzaSelectionIntent.SelectGroupVariationsIntent -> PizzaSelectionAction.SelectVariation(intent.groupVariations)
            else -> throw IllegalArgumentException("unknown intent")
        }
    }


    private fun compose(): Observable<PizzaSelectionViewState> {
        return intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(pizzaActionProcessorHolder.transformerFromAction())
                .scan(PizzaSelectionViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    companion object {
        private val reducer = BiFunction { previousState: PizzaSelectionViewState, result: PizzaSelectionResult ->
            when (result) {
                is PizzaSelectionResult.LoadPizaVariationsResult -> when (result) {
                    is PizzaSelectionResult.LoadPizaVariationsResult.Success -> {
                        previousState.copy(
                                isLoading = false,
                                groups = result.variations
                        )
                    }
                    is PizzaSelectionResult.LoadPizaVariationsResult.Failure -> {
                        previousState.copy()
                    }

                    is PizzaSelectionResult.LoadPizaVariationsResult.InFlight ->
                        previousState.copy(isLoading = true)

                }
                is PizzaSelectionResult.SelectPizzaVariationResult -> when (result) {
                    is PizzaSelectionResult.SelectPizzaVariationResult.Success -> {
                        val groups = mutableListOf<SelectableVariationGroupItems>()
                        for (group in previousState.groups) {
                            val list = mutableListOf<SelectableVariations>()
                            if (result.selectedVariation.groupId == group.groupId) {
                                for (variation in group.variations) {
                                    if (result.selectedVariation.variationId == variation.variationsItem.id) {
                                        list.add(SelectableVariations(variationsItem = variation.variationsItem,
                                                isSelected = true,
                                                isExcluded = false))
                                    } else {
                                        list.add(SelectableVariations(variationsItem = variation.variationsItem,
                                                isSelected = false,
                                                isExcluded = false))
                                    }
                                }
                            } else {
                                for (variation in group.variations) {
                                    list.add(SelectableVariations(variationsItem = variation.variationsItem,
                                            isSelected = true,
                                            isExcluded = false))
                                }
                            }
                            groups.add(SelectableVariationGroupItems(groupId = group.groupId, variations = list, name = group.name))
                        }
                        previousState.copy(groups = groups)
                    }

                    is PizzaSelectionResult.SelectPizzaVariationResult.Failure -> {
                        previousState.copy()
                    }

                    is PizzaSelectionResult.SelectPizzaVariationResult.InFlight -> {
                        previousState.copy()
                    }

                }

            }
        }
    }
}