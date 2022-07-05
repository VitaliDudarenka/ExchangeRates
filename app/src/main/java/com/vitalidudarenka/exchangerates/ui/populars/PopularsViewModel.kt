package com.vitalidudarenka.exchangerates.ui.populars

import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.usecases.GetFavoritesUseCase
import com.vitalidudarenka.domain.usecases.GetPopularsUseCase
import com.vitalidudarenka.domain.usecases.PostFavoritesUseCase
import com.vitalidudarenka.exchangerates.app.App
import com.vitalidudarenka.exchangerates.base.BaseListViewModel
import com.vitalidudarenka.exchangerates.states.ResultsState
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularsViewModel : BaseListViewModel() {

    init {
        App.appComponent.inject(this)
    }

    fun getRates(symbol: Symbol) {
        scope.launch {
            ratesState.value = ResultsState.Loading
            val data = getPopularsUseCase.getRates(symbol, null)
            if (data is ResultWrapper.Success) {
                initialRatesList = data.data
                onRatesLoaded()
            } else if (data is ResultWrapper.Error)
                ratesState.value = ResultsState.Error(data.throwable)
        }
    }

}