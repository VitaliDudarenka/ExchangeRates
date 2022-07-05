package com.vitalidudarenka.domain.usecases

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import com.vitalidudarenka.domain.repostiroies.SymbolsRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository
) :
    BaseUseCase() {

    suspend fun getRates(): List<Rate> {
        return ratesRepository.getFavorites()
    }

}