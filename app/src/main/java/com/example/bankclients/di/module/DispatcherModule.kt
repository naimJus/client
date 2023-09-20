package com.example.bankclients.di.module

import com.example.bankclients.di.qualifier.IoDispatcher
import com.example.bankclients.di.qualifier.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class DispatcherModule {

    @Provides
    @Singleton
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineContext {
        return Dispatchers.Main
    }
}