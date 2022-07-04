package com.vitalidudarenka.exchangerates.ui.populars

import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.usecases.GetPopularsUseCase
import com.vitalidudarenka.exchangerates.app.App
import com.vitalidudarenka.exchangerates.base.BaseListViewModel
import com.vitalidudarenka.exchangerates.states.ResultsState
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val USD_CODE = "USD"

class PopularsViewModel : BaseListViewModel() {

    @Inject
    lateinit var getPopularsUseCase: GetPopularsUseCase

    init {
        App.appComponent.inject(this)
    }

    fun getSymbols() {
        scope.launch {
            symbolsState.value = ResultsState.Loading
            val data = getPopularsUseCase.getSymbols()
            if (data is ResultWrapper.Success) {
                symbolsState.value = ResultsState.SymbolsLoaded(data.data)
                initialSymbolsList = data.data
                val defaultCurrency = data.data.find { it.code == USD_CODE } ?: data.data.first()
                selectedCurrency.postValue(defaultCurrency)
            } else if (data is ResultWrapper.Error)
                symbolsState.value = ResultsState.Error(data.throwable)
        }
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