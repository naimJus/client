package com.example.bankclients.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.User
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

    }

    private val _usersFlow = MutableStateFlow<List<User>>(emptyList())
    val usersFlow: StateFlow<List<User>> = _usersFlow

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            when (val result = getUsersUseCase(true)) {
                is Result.Error -> _usersFlow.value = emptyList()
                is Result.Success -> _usersFlow.value = result.data
            }
        }
    }
}