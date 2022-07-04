package com.vitalidudarenka.data.repositories

import com.vitalidudarenka.data.network.RestService
import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import java.util.*

class RatesRepositoryImpl(private val apiService: RestService):RatesRepository {

    override suspend fun getRates(currency: Currency): ResultWrapper<List<Rate>> {
        TODO("Not yet implemented")
    }

}