package com.softyorch.firestoreadvanced2compose.domain.dto

import com.google.firebase.Timestamp
import java.util.Date

data class TransactionDTO(
    val id: String = Date().time.toString(),
    val title: String,
    val amount: Double,
    val date: Timestamp,
) {
    companion object {
        fun prepareDTO(title: String, amount: String, date: Long?): TransactionDTO? {
            if (title.isBlank() || amount.isBlank()) return null
            val timeStamp = if (date != null) {
                val seconds = date / 1_000
                val nanoseconds = ((date % 1_000) * 1_000_000).toInt()
                Timestamp(seconds, nanoseconds)
            } else {
                Timestamp.now()
            }

            return try {
                TransactionDTO(title = title, amount = amount.toDouble(), date = timeStamp)
            } catch (ex: Exception) {
                null
            }
        }
    }
}
