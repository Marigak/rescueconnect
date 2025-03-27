package com.rescueconnect.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rescueconnect.app.data.Donation
import com.rescueconnect.app.data.DonationRepository
import kotlinx.coroutines.launch

@Composable
fun DonateScreen(navController: NavController) {
    val repository = remember { DonationRepository() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var itemName by remember { mutableStateOf("") } // ✅ Changed from "amount" to "itemName"
    var donorName by remember { mutableStateOf("") } // ✅ Changed from "description" to "donorName"
    var quantity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Donate") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = donorName,
                onValueChange = { donorName = it },
                label = { Text("Your Name") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val donation = Donation(
                        itemName = itemName,
                        donorName = donorName,
                        quantity = quantity,
                        location = location
                    )

                    coroutineScope.launch {
                        isSubmitting = true
                        val success = repository.addDonation(donation) // ✅ Fixed function call
                        isSubmitting = false

                        if (success) {
                            Toast.makeText(context, "Donation submitted!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Failed to submit donation", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = !isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSubmitting) "Submitting..." else "Submit")
            }
        }
    }
}
