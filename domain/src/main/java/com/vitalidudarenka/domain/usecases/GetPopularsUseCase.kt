package com.vitalidudarenka.domain.usecases

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import com.vitalidudarenka.domain.repostiroies.SymbolsRepository
import java.util.*
import javax.inject.Inject

class GetPopularsUseCase @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val symbolsRepository: SymbolsRepository
) :
    BaseUseCase() {

    suspend fun getSymbols(): ResultWrapper<List<Symbol>> {
        return symbolsRepository.getSymbolsRemote()
    }

    suspend fun getRates(currency: Currency): ResultWrapper<List<Rate>> {
        return ratesRepository.getRates(currency)
    }

}