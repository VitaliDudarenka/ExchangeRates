package com.vitalidudarenka.data.repositories

import com.vitalidudarenka.data.db.dao.RateDao
import com.vitalidudarenka.data.db.entities.transformToDB
import com.vitalidudarenka.data.db.entities.transformToDomain
import com.vitalidudarenka.data.network.RestService
import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RatesRepositoryImpl(private val apiService: RestService, private val rateDao: RateDao) :
    RatesRepository {

    override suspend fun getRates(
        baseSymbol: Symbol,
        rates: List<Rate>?
    ): ResultWrapper<List<Rate>> {
        var symbolsString: String? = null
        if (rates != null)
            symbolsString = rates.joinToString(separator = ",") { it.name }

        return try {
            val ratesResponse = apiService.api.getRatesAsync(baseSymbol.code, symbolsString).await()
            val rates: MutableList<Rate> = mutableListOf()
            for ((k, v) in ratesResponse.rates) {
                rates.add(Rate(k, v))
            }
            ResultWrapper.Success(rates)
        } catch (e: Throwable) {
            ResultWrapper.Error(e)
        }
    }

    override fun getFavorites(): Flow<List<Rate>> {
        return rateDao.getAll().map { it.map { rate -> rate.transformToDomain() } }
    }

    override suspend fun saveFavorite(rate: Rate) {
        rateDao.insert(rate.transformToDB())
    }

    override suspend fun removeFavorite(rate: Rate) {
        rateDao.delete(rate.transformToDB())
    }

}