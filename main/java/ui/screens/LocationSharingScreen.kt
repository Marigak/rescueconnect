package com.rescueconnect.app.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rescueconnect.app.data.LocationRepository
import kotlinx.coroutines.launch

@Composable
fun LocationSharingScreen(navController: NavController, locationRepository: LocationRepository) {
    var status by remember { mutableStateOf("Not Shared") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = { TopAppBar(title = { Text("Share Location") }) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Tap the button to share your location with emergency responders.")

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val success = locationRepository.shareLocation() // ✅ Fix: Ensure this function exists
                        status = if (success) "Location Shared" else "Failed to Share Location"
                    }
                }
            ) {
                Text("Share My Location")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Status: $status", style = MaterialTheme.typography.h6)
        }
    }
}
