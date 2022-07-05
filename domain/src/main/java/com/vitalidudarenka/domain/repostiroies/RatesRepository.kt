package com.vitalidudarenka.domain.repostiroies

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import kotlinx.coroutines.flow.Flow

interface RatesRepository : BaseRepository {

    suspend fun getRates(baseSymbol: Symbol, rates: List<Rate>?): ResultWrapper<List<Rate>>

    fun getFavorites(): Flow<List<Rate>>

    suspend fun saveFavorite(rate: Rate)

    suspend fun removeFavorite(rate: Rate)

}