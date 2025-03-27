package com.rescueconnect.app.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class SafetyStatus(
    val userId: String = "",
    val fullName: String = "",
    val status: String = "Unknown", // "Safe" or "In Danger"
    val timestamp: Long = System.currentTimeMillis()
)

class SafetyCheckRepository {
    private val db = FirebaseFirestore.getInstance()
    private val safetyCollection = db.collection("safety_status")

    suspend fun updateSafetyStatus(userId: String, fullName: String, status: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val safetyStatus = SafetyStatus(userId, fullName, status, System.currentTimeMillis())
            safetyCollection.document(userId).set(safetyStatus).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getSafetyStatus(): List<SafetyStatus> = withContext(Dispatchers.IO) {
        return@withContext try {
            val snapshot = safetyCollection.orderBy("timestamp").get().await()
            snapshot.documents.mapNotNull { it.toObject(SafetyStatus::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
