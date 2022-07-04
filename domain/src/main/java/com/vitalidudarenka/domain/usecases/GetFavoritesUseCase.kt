package com.vitalidudarenka.domain.usecases

import com.vitalidudarenka.domain.repostiroies.RatesRepository
import com.vitalidudarenka.domain.repostiroies.SymbolsRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val symbolsRepository: SymbolsRepository
) :
    BaseUseCase() {
}