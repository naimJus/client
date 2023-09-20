package com.example.data.di;

import com.example.data.api.UserApi;
import com.example.data.datasource.RemoteUserDataSource;
import com.example.data.datasource.UserDataSource;
import com.example.data.repository.BankUserRepository;
import com.example.data.repository.UserRepository;

import org.jetbrains.annotations.NotNull;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class DataModule {

    @Binds
    public abstract UserDataSource bindDataSource(RemoteUserDataSource dataSource);

    @Binds
    public abstract UserRepository bindUserRepository(BankUserRepository userRepository);

    @Provides
    public static UserApi provideUserApi(@NotNull Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

}
