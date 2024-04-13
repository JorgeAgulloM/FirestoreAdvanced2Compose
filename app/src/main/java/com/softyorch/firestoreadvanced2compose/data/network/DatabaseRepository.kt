package com.softyorch.firestoreadvanced2compose.data.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.softyorch.firestoreadvanced2compose.data.response.TransactionResponse
import com.softyorch.firestoreadvanced2compose.data.response.TransactionResponse.Companion.toDomain
import com.softyorch.firestoreadvanced2compose.domain.dto.TransactionDTO
import com.softyorch.firestoreadvanced2compose.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val db: FirebaseFirestore) {

    companion object {
        const val USER_COLLECTION = "yorch"
        const val FILE_DATE = "date"
    }

    fun getTransactions(): Flow<List<TransactionModel>> {
        return db.collection(USER_COLLECTION)
            .orderBy(FILE_DATE, Query.Direction.DESCENDING)
            .snapshots()
            .map { qs ->
                qs.toObjects(TransactionResponse::class.java)
                    .mapNotNull { transactionResponse ->
                        transactionResponse.toDomain()
                    }
            }
    }

    fun addTransaction(dto: TransactionDTO) {
        dto.apply {
            val hashModel = hashMapOf(
                "id" to id,
                "title" to title,
                "date" to date,
                "amount" to amount
            )
            db.collection(USER_COLLECTION).document(id).set(hashModel)
        }
    }

}
