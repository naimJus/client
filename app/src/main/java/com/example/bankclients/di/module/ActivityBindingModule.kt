package com.example.bankclients.di.module

import com.example.bankclients.di.qualifier.PerActivity
import com.example.bankclients.ui.list.UsersActivity
import com.example.bankclients.ui.list.UsersUiModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [AppComponent]. You never
 * need to tell [AppComponent] that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that [AppComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [@ActivityScoped].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [UsersUiModule::class]
    )
    internal abstract fun contributeUsersUiModule(): UsersActivity
}
