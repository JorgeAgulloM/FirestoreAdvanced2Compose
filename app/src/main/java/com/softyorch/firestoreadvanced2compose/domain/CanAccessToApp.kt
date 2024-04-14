package com.softyorch.firestoreadvanced2compose.domain

import com.softyorch.firestoreadvanced2compose.data.network.DatabaseRepository
import javax.inject.Inject

class CanAccessToApp @Inject constructor(private val db: DatabaseRepository) {
    suspend operator fun invoke(): Boolean {
        val minVersion = db.getMinAllowedVersion()
        val appVersion = db.getAppVersion()

        return appVersion.zip(minVersion).all {  (appV, minV) -> appV >= minV }
    }
}
