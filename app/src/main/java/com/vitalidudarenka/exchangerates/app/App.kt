package com.vitalidudarenka.exchangerates.app

import android.app.Application
import com.vitalidudarenka.exchangerates.di.AppComponent
import com.vitalidudarenka.exchangerates.di.AppModule
import com.vitalidudarenka.exchangerates.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var instance: App

        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    init {
        instance = this
    }

    override fun onCreate() {

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        super.onCreate()

    }

}