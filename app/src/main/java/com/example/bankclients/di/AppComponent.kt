package com.example.bankclients.di

import com.example.bankclients.BankClientApplication
import com.example.bankclients.di.module.ActivityBindingModule
import com.example.bankclients.di.module.AppModule
import com.example.bankclients.di.module.NetworkModule
import com.example.bankclients.di.module.ViewModelModule
import com.example.data.di.DataModule
import com.example.data.di.DataSubcomponent
import com.example.domain.di.DomainModule
import com.example.domain.di.DomainSubcomponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        DomainModule::class,
        DataModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<BankClientApplication> {

    fun dataSubcomponentFactory(): DataSubcomponent.Factory
    fun domainSubcomponentFactory(): DomainSubcomponent.Factory


    @Component.Factory
    interface Factory {
        fun create(module: AppModule): AppComponent
    }
}