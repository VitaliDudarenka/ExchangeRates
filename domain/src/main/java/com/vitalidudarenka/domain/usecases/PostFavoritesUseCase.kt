package com.vitalidudarenka.domain.usecases

import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import javax.inject.Inject

class PostFavoritesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository
) :
    BaseUseCase() {

    suspend fun saveFavorite(rate: Rate) {
        ratesRepository.saveFavorite(rate)
    }

    suspend fun removeFavorite(rate: Rate) {
        ratesRepository.removeFavorite(rate)
    }

}