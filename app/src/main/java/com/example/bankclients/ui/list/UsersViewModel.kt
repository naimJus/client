package com.example.bankclients.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankclients.R
import com.example.data.model.exception.UserFetchException
import com.example.domain.model.Result
import com.example.domain.model.UserItem
import com.example.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorsFlow.tryEmit(parseError(throwable))
    }

    private val _errorsFlow = MutableStateFlow<Alert?>(null)
    val errorsFlow: StateFlow<Alert?> = _errorsFlow

    private val _usersFlow = MutableStateFlow<UiState>(UiState.Loading)
    val usersFlow: StateFlow<UiState> = _usersFlow

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _usersFlow.emit(UiState.Loading)
            when (val result = getUsersUseCase(true)) {
                is Result.Success -> _usersFlow.emit(UiState.Items(result.data))
                is Result.Error -> {
                    _usersFlow.emit(UiState.Items(emptyList()))
                    _errorsFlow.emit(parseUserFetchException(result.error))
                }
            }
        }
    }

    private fun parseError(throwable: Throwable): Alert {
        return if (throwable is UserFetchException) {
            parseUserFetchException(throwable)
        } else {
            Alert(R.string.something_went_wrong, R.string.please_try_again_later, android.R.string.cancel)
        }
    }

    private fun parseUserFetchException(userFetchException: UserFetchException): Alert {
        return when (userFetchException) {
            UserFetchException.NetworkException -> Alert(
                R.string.internet_access_not_available,
                R.string.check_your_internet_connection,
                android.R.string.ok
            )

            else -> Alert(
                R.string.something_went_wrong,
                R.string.please_try_again_later,
                android.R.string.ok
            )
        }
    }
}

sealed interface UiState {
    object Loading : UiState
    data class Items(val users: List<UserItem>) : UiState
    data class Toast(val message: Int) : UiState
}

data class Alert(val title: Int, val message: Int, val button: Int)