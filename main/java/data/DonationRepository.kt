package com.rescueconnect.app.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Donation(
    val itemName: String = "",
    val donorName: String = "",
    val quantity: String = "",
    val location: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

class DonationRepository {
    private val db = FirebaseFirestore.getInstance()
    private val donationsCollection = db.collection("donations")

    suspend fun addDonation(donation: Donation): Boolean {
        return try {
            donationsCollection.add(donation).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getDonations(): List<Donation>? {
        return try {
            val snapshot = donationsCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Donation::class.java) }
        } catch (e: Exception) {
            null
        }
    }
}
