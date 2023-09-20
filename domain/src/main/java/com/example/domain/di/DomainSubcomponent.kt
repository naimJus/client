package com.example.domain.di

import com.example.data.repository.UserRepository
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [DomainModule::class])
interface DomainSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance userRepository: UserRepository): DomainSubcomponent
    }
}