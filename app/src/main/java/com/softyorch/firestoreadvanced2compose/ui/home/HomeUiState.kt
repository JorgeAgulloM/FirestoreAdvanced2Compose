package com.softyorch.firestoreadvanced2compose.ui.home

import com.softyorch.firestoreadvanced2compose.domain.model.TransactionModel

data class HomeUiState(
    val isLoading: Boolean = true,
    val transactions: List<TransactionModel> = emptyList(),
    val totalAmount: String? = null,
)
