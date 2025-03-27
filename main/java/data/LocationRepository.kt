package com.rescueconnect.app.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LocationRepository(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val locationCollection = db.collection("locations")
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // Make sure to request location permissions before calling this function
    suspend fun shareLocation(): Boolean {
        return try {
            val location: Location? = fusedLocationClient.lastLocation.await()
            if (location != null) {
                val locationData = mapOf(
                    "latitude" to location.latitude,
                    "longitude" to location.longitude,
                    "timestamp" to System.currentTimeMillis()
                )
                locationCollection.add(locationData).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
