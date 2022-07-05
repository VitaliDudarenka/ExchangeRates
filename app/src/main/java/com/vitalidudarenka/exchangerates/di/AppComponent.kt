package com.vitalidudarenka.exchangerates.di

import com.vitalidudarenka.exchangerates.ui.favorites.FavoritesViewModel
import com.vitalidudarenka.exchangerates.ui.populars.PopularsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(popularsViewModel: PopularsViewModel)
    fun inject(favoritesViewModel: FavoritesViewModel)

}