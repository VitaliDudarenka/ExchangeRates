package com.vitalidudarenka.data.network

import com.vitalidudarenka.data.entities.RatesResponse
import com.vitalidudarenka.data.entities.SymbolsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("symbols")
    fun getSymbolsAsync(): Deferred<SymbolsResponse>

    @GET("latest")
    fun getRatesAsync(@Query("base") base: String,
                        @Query("symbols") symbols: String?): Deferred<RatesResponse>

}