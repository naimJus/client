package com.example.bankclients

import com.example.bankclients.di.DaggerAppComponent
import com.example.bankclients.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BankClientApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
//        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(AppModule(this))
    }
}