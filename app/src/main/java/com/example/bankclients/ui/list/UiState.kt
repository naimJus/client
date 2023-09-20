package com.example.bankclients.ui.list

import com.example.domain.model.UserItem

sealed interface UiState {
    object Loading : UiState
    data class Items(val users: List<UserItem>) : UiState
}