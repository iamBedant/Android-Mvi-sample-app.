package com.iambedant.pizzaapp.feature.selection

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.iambedant.pizzaapp.R
import com.iambedant.pizzaapp.data.SelectVariation
import com.iambedant.pizzaapp.data.SelectableVariationGroupItems
import com.iambedant.pizzaapp.mvibase.MviView
import com.iambedant.pizzaapp.util.gone
import com.iambedant.pizzaapp.util.visible
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_pizza_selection.*
import kotlinx.android.synthetic.main.layout_variations.view.*
import javax.inject.Inject

/**
 * Created by @iamBedant on 15/05/18.
 */
class PizzaSelectionActivity : DaggerAppCompatActivity(), MviView<PizzaSelectionIntent, PizzaSelectionViewState>, HasActivityInjector {
    @Inject
    lateinit var factory: PizzaSelectionViewmodelFactory

    private fun initialIntent(): Observable<PizzaSelectionIntent.InitialIntent> {
        return Observable.just(PizzaSelectionIntent.InitialIntent)
    }

    private val selectGroupVariationsIntent = PublishSubject.create<PizzaSelectionIntent.SelectGroupVariationsIntent>()

    override fun intents(): Observable<PizzaSelectionIntent> {
        return Observable.merge(initialIntent(),
                selectGroupVariationsIntent
        )
    }


    override fun render(state: PizzaSelectionViewState) {
        with(state) {
            when (isLoading) {
                true -> progressBar.visible()
                false -> progressBar.gone()
            }
            if (!groups.isEmpty()) {
                for (group in groups) {
                    container.addView(populateRadioUi(group))
                }
            }
        }
    }

    private fun populateRadioUi(group: SelectableVariationGroupItems): View {
        val view = layoutInflater.inflate(R.layout.layout_variations, null)
        view.groupName.text = group.name
        val radioGroup = RadioGroup(this)
        with(radioGroup) {
            orientation = RadioGroup.VERTICAL
            setPadding(24, 0, 0, 24)
        }
        for (items in group.variations) {
            val radioButton = RadioButton(this)
            with(radioButton) {
                tag = items.variationsItem.id
                text = items.variationsItem.name
                radioGroup.addView(radioButton)
                if (items.isSelected && !items.isExcluded) {
                    radioGroup.check(radioButton.id)
                }
                if(items.isExcluded){
                    radioButton.isEnabled = false
                }
//                setOnCheckedChangeListener { _, isChecked ->
//                    if (isChecked) {
//                        selectGroupVariationsIntent.onNext(PizzaSelectionIntent.SelectGroupVariationsIntent(SelectVariation(
//                                groupId = group.groupId, variationId = items.variationsItem.id)))
//                    }
//                }
            }

        }
        (view as LinearLayout).addView(radioGroup)
        return view
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    private val viewModel: PizzaSelectionViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, factory).get(PizzaSelectionViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_selection)
        bind()
    }

    private fun bind() {
        viewModel.processIntents(intents())
        viewModel.states().observe(this, Observer {
            if (it != null) {
                render(it)
            }
        })
    }

}
