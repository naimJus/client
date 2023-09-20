package com.example.data.di

import dagger.BindsInstance
import dagger.Subcomponent
import retrofit2.Retrofit

@Subcomponent(modules = [DataModule::class])
interface DataSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance retrofit: Retrofit): DataSubcomponent
    }
}