package com.vitalidudarenka.exchangerates.base

import androidx.lifecycle.MutableLiveData
import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.usecases.GetFavoritesUseCase
import com.vitalidudarenka.domain.usecases.PostFavoritesUseCase
import com.vitalidudarenka.exchangerates.extensions.default
import com.vitalidudarenka.exchangerates.states.ResultsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseListViewModel : BaseViewModel() {

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

    fun getFavorites(){
        scope.launch {
            val favorites = getFavoritesUseCase.getRates()
            favoritesRates.postValue(favorites)
        }
    }

    fun saveFavorite(rate: Rate){
        scope.launch {
            postFavoritesUseCase.saveFavorite(rate)
            getFavorites()
        }
    }

    fun removeFavorite(rate: Rate){
        scope.launch {
            postFavoritesUseCase.removeFavorite(rate)
            getFavorites()
        }
    }

    enum class SortType {
        NAME_UP,
        NAME_DOWN,
        VALUE_UP,
        VALUE_DOWN
    }

}