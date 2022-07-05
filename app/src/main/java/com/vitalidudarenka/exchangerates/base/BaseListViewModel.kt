package com.vitalidudarenka.exchangerates.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.usecases.GetFavoritesUseCase
import com.vitalidudarenka.domain.usecases.GetPopularsUseCase
import com.vitalidudarenka.domain.usecases.PostFavoritesUseCase
import com.vitalidudarenka.exchangerates.extensions.default
import com.vitalidudarenka.exchangerates.states.ResultsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val USD_CODE = "USD"

open class BaseListViewModel : BaseViewModel() {

    @Inject
    lateinit var getPopularsUseCase: GetPopularsUseCase

    @Inject
    lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Inject
    lateinit var postFavoritesUseCase: PostFavoritesUseCase

    val symbolsState = MutableStateFlow<ResultsState>(ResultsState.Default)
    val ratesState = MutableStateFlow<ResultsState>(ResultsState.Default)

    var selectedCurrency = MutableLiveData<Symbol>()
    var selectedSortType = MutableLiveData<SortType>().default(SortType.NAME_UP)
    var favoritesRates = MutableLiveData<List<Rate>>().default(emptyList())
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

    fun getFavorites() {
        scope.launch {
            getFavoritesUseCase.getRates().collect {
                favoritesRates.postValue(it)
            }
        }
    }

    fun saveFavorite(rate: Rate) {
        scope.launch {
            postFavoritesUseCase.saveFavorite(rate)
        }
    }

    fun removeFavorite(rate: Rate) {
        scope.launch {
            postFavoritesUseCase.removeFavorite(rate)
        }
    }

    enum class SortType {
        NAME_UP,
        NAME_DOWN,
        VALUE_UP,
        VALUE_DOWN
    }

}