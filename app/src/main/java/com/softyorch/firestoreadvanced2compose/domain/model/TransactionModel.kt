package com.softyorch.firestoreadvanced2compose.domain.model

data class TransactionModel(
    val id: Int,
    val title: String,
    val amount: String,
    val date: String,
)
