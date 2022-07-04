package com.vitalidudarenka.exchangerates.app

import android.app.Application
import com.vitalidudarenka.exchangerates.di.AppComponent
import com.vitalidudarenka.exchangerates.di.AppModule
import com.vitalidudarenka.exchangerates.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .build()

    }

}