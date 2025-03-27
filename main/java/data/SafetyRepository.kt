package com.rescueconnect.app.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SafetyRepository {
    private val db = FirebaseFirestore.getInstance()
    private val safetyCollection = db.collection("safety_status")
    private val auth = FirebaseAuth.getInstance()

    fun updateSafetyStatus(status: String, onComplete: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        val safetyData = mapOf(
            "userId" to userId,
            "status" to status,
            "timestamp" to System.currentTimeMillis()
        )

        safetyCollection.document(userId).set(safetyData)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getUserSafetyStatus(onResult: (String) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        safetyCollection.document(userId).get()
            .addOnSuccessListener { document ->
                val status = document.getString("status") ?: "Unknown"
                onResult(status)
            }
            .addOnFailureListener {
                onResult("Error retrieving status")
            }
    }
}
