package com.team6.smartbudget

import android.app.Application
import android.content.Context
import com.team6.smartbudget.shared.di.AppComponent
import com.team6.smartbudget.shared.di.DaggerAppComponent

class SmartBudgetApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent
                .factory()
                .create(this)

        appComponent.configs.forEach { config ->
            config.configure()
        }
    }
}

val Context.component: AppComponent
    get() = (applicationContext as SmartBudgetApp).appComponent
