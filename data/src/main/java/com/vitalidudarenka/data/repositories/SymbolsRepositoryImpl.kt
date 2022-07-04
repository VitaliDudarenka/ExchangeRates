package com.vitalidudarenka.data.repositories

import com.vitalidudarenka.data.network.RestService
import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.domain.repostiroies.SymbolsRepository

class SymbolsRepositoryImpl(private val apiService: RestService) : SymbolsRepository {

    override suspend fun getSymbolsRemote(): ResultWrapper<List<Symbol>> {
        return try {
            val symbolsResponse = apiService.api.getSymbolsAsync().await()
            val symbols: MutableList<Symbol> = mutableListOf()
            for ((k, v) in symbolsResponse.symbols) {
                symbols.add(Symbol(k, v))
            }
            ResultWrapper.Success(symbols)
        } catch (e: Throwable) {
            ResultWrapper.Error(e)
        }
    }

    override suspend fun getFavoriteSymbols(): ResultWrapper<List<Symbol>> {
        TODO("Not yet implemented")
    }

}