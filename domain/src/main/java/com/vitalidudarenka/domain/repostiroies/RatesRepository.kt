package com.vitalidudarenka.domain.repostiroies

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import java.util.*

interface RatesRepository : BaseRepository {

    suspend fun getRates(currency: Currency): ResultWrapper<List<Rate>>

}