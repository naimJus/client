package com.example.domain.di;

import com.example.data.model.User;
import com.example.data.model.exception.UserFetchException;
import com.example.domain.model.Result;
import com.example.domain.usecase.GetUserByIdUseCase;
import com.example.domain.usecase.GetUsersUseCase;
import com.example.domain.usecase.UseCase;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DomainModule {

    @Binds
    public abstract UseCase<Integer, Result<User, UserFetchException>> bindGetUserByIdUseCase(GetUserByIdUseCase useCase);

    @Binds
    public abstract UseCase<Boolean, Result<User, UserFetchException>> bindGetUsersCase(GetUsersUseCase useCase);
}
