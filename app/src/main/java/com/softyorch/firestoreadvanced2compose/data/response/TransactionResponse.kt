package com.softyorch.firestoreadvanced2compose.data.response

import com.google.firebase.Timestamp
import com.softyorch.firestoreadvanced2compose.domain.model.TransactionModel
import java.text.SimpleDateFormat
import java.util.Locale

data class TransactionResponse(
    val id: String? = null,
    val title: String? = null,
    val amount: Double? = null,
    val date: Timestamp? = null,
) {
    companion object {
        fun TransactionResponse.toDomain(): TransactionModel? {
            if (this.date == null || this.amount == null || this.title == null || this.id == null) return null

            val date = this.date.toDateString() ?: return null

            return TransactionModel(
                id = this.id,
                title = this.title,
                amount = this.amount,
                date = date
            )
        }

        private fun Timestamp?.toDateString(): String? {
            this ?: return null
            return try {
                val date = this.toDate()
                val sdf = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                sdf.format(date)
            } catch (e: Exception) {
                null
            }
        }
    }
}
