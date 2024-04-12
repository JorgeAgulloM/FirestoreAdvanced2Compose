package com.softyorch.firestoreadvanced2compose.domain.model

data class TransactionModel(
    val id: String,
    val title: String,
    val amount: Double,
    val date: String,
) {
    companion object {
        fun TransactionModel.amountFormatted(): String = String.format("%.2f", this.amount)
    }
}
