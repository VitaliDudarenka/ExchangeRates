package com.vitalidudarenka.domain.usecases

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import com.vitalidudarenka.domain.repostiroies.SymbolsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository
) :
    BaseUseCase() {

    fun getRates(): Flow<List<Rate>> {
        return ratesRepository.getFavorites()
    }

}