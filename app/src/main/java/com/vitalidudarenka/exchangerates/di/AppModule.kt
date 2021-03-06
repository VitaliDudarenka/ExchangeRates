package com.vitalidudarenka.exchangerates.di

import android.content.Context
import com.vitalidudarenka.data.db.AppDataBase
import com.vitalidudarenka.data.db.dao.RateDao
import com.vitalidudarenka.data.network.RestService
import com.vitalidudarenka.data.repositories.RatesRepositoryImpl
import com.vitalidudarenka.data.repositories.SymbolsRepositoryImpl
import com.vitalidudarenka.domain.repostiroies.RatesRepository
import com.vitalidudarenka.domain.repostiroies.SymbolsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

const val URL_INJECT_NAME = "url_name"

@Module
class AppModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun provideRatesRepository(restService: RestService, rateDao: RateDao): RatesRepository =
        RatesRepositoryImpl(restService, rateDao)

    @Provides
    @Singleton
    fun provideSymbolsRepository(restService: RestService): SymbolsRepository =
        SymbolsRepositoryImpl(restService)

    @Provides
    @Singleton
    fun provideRateDao(appDataBase: AppDataBase): RateDao = appDataBase.getRateDao()

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): AppDataBase = AppDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideRestService(
        @Named(URL_INJECT_NAME) serverUrl: String
    ): RestService =
        RestService(context, serverUrl)

    @Provides
    @Named(URL_INJECT_NAME)
    fun provideServerUrlDebug(): String = "https://api.apilayer.com/exchangerates_data/"


}