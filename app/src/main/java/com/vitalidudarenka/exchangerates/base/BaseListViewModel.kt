package com.vitalidudarenka.exchangerates.base

import androidx.lifecycle.MutableLiveData
import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.exchangerates.extensions.default
import com.vitalidudarenka.exchangerates.states.ResultsState
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseListViewModel : BaseViewModel() {

    val symbolsState = MutableStateFlow<ResultsState>(ResultsState.Default)
    val ratesState = MutableStateFlow<ResultsState>(ResultsState.Default)

    var selectedCurrency = MutableLiveData<Symbol>()
    var selectedSortType = MutableLiveData<SortType>().default(SortType.NAME_UP)
    protected var initialRatesList = emptyList<Rate>()
    var initialSymbolsList = emptyList<Symbol>()

    fun onRatesLoaded() {
        val sortedList = initialRatesList.toMutableList()
        when (selectedSortType.value) {
            SortType.NAME_UP -> {
                sortedList.sortBy { it.name }
            }
            SortType.NAME_DOWN -> {
                sortedList.sortByDescending { it.name }
            }
            SortType.VALUE_UP -> {
                sortedList.sortBy { it.value }
            }
            SortType.VALUE_DOWN -> {
                sortedList.sortByDescending { it.value }
            }
        }
        ratesState.value = ResultsState.RatesLoaded(sortedList)
    }

    enum class SortType {
        NAME_UP,
        NAME_DOWN,
        VALUE_UP,
        VALUE_DOWN
    }

}