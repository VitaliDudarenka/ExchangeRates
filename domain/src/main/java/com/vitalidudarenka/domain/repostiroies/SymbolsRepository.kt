package com.vitalidudarenka.domain.repostiroies

import com.vitalidudarenka.domain.entities.ResultWrapper
import com.vitalidudarenka.domain.entities.Symbol

interface SymbolsRepository : BaseRepository {

    suspend fun getSymbolsRemote(): ResultWrapper<List<Symbol>>

    suspend fun getFavoriteSymbols(): ResultWrapper<List<Symbol>>

}