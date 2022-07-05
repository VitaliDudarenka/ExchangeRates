package com.vitalidudarenka.exchangerates.ui.favorites

import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.exchangerates.app.App
import com.vitalidudarenka.exchangerates.base.BaseListViewModel
import com.vitalidudarenka.exchangerates.states.ResultsState
import kotlinx.coroutines.launch

class FavoritesViewModel : BaseListViewModel() {

    init {
        App.appComponent.inject(this)
    }

    fun getRates(symbol: Symbol, setLoadingState: Boolean) {
        scope.launch {
            if (setLoadingState)
                ratesState.value = ResultsState.Loading
            val data = getPopularsUseCase.getRates(symbol, favoritesRates.value)
            if (data is ResultWrapper.Success) {
                initialRatesList = data.data
                onRatesLoaded()
            } else if (data is ResultWrapper.Error)
                ratesState.value = ResultsState.Error(data.throwable)
        }
    }

}