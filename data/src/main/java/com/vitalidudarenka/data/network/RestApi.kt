package com.vitalidudarenka.data.network

import com.vitalidudarenka.data.entities.SymbolsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RestApi {

    @GET("/symbols")
    fun getSymbolsAsync(): Deferred<SymbolsResponse>
}