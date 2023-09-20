package com.example.bankclients.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.domain.model.Result
import com.example.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _usersFlow.value = parseError(throwable)
    }

    private val _usersFlow = MutableStateFlow<UiState>(UiState.Empty)
    val usersFlow: StateFlow<UiState> = _usersFlow

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when (val result = getUsersUseCase(true)) {
                is Result.Error -> _usersFlow.emit(parseUserFetchException(result.error))
                is Result.Success -> _usersFlow.emit(UiState.Items(result.data))
            }
        }
    }

    private fun parseError(throwable: Throwable): UiState {
        return if (throwable is UserFetchException) {
            parseUserFetchException(throwable)
        } else {
            TODO()
        }
    }

    private fun parseUserFetchException(userFetchException: UserFetchException): UiState {
        when (userFetchException) {
            UserFetchException.CacheNotAvailableException -> TODO()
            UserFetchException.NetworkException -> TODO()
            is UserFetchException.NotFoundException -> TODO()
            is UserFetchException.UnknownException -> TODO()
        }
    }
}

sealed interface UiState {
    object Empty : UiState
    object Loading : UiState
    data class Items(val users: List<User>) : UiState
    data class Error(val error: Int) : UiState
    data class Alert(val title: Int, val message: Int, val button: Int) : UiState
}