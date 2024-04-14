package com.softyorch.firestoreadvanced2compose.data.network

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.softyorch.firestoreadvanced2compose.data.response.TransactionResponse
import com.softyorch.firestoreadvanced2compose.data.response.TransactionResponse.Companion.toDomain
import com.softyorch.firestoreadvanced2compose.domain.dto.TransactionDTO
import com.softyorch.firestoreadvanced2compose.domain.model.TransactionModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: FirebaseFirestore,
    private val remoteConfig: FirebaseRemoteConfig,
) {

    companion object {
        const val USER_COLLECTION = "yorch"
        const val FILE_DATE = "date"

        // Remote config params
        const val TITLE_PARAM = "title"
        const val FELIZ_PARAM = "feliz"
        const val MIN_VERSION = "min_version"
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

    fun deleteTransaction(id: String) {
        db.collection(USER_COLLECTION).document(id).delete()
    }

    fun getAppInfo(): String {
        val title = remoteConfig.getString(TITLE_PARAM)
        val feliz = remoteConfig.getBoolean(FELIZ_PARAM)
        return "El título $title está feliz?: $feliz"
    }

    fun getAppVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName.split(".").map { it.toInt() }
        } catch (ex: Exception) {
            listOf(0, 0, 0)
        }
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        // Solo para obtener valores urgentes, y nunca usar 0 seg.
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        //
        val minVersion = remoteConfig.getString(MIN_VERSION)
        if (minVersion.isBlank()) return listOf(0, 0, 0)

        return minVersion.split(".").map { it.toInt() }
    }

}
