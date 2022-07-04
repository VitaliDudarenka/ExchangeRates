package com.vitalidudarenka.exchangerates.states

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.Symbol

sealed class ResultsState {

    class SymbolsLoaded(val data: List<Symbol>) : ResultsState()
    class RatesLoaded(val data: List<Rate>) : ResultsState()
    object Default : ResultsState()
    object Loading : ResultsState()
    class Error(val error: Throwable) : ResultsState()

}