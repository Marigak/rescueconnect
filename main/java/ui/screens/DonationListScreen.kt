package com.rescueconnect.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rescueconnect.app.data.Donation
import com.rescueconnect.app.data.DonationRepository
import kotlinx.coroutines.launch

@Composable
fun DonationListScreen(navController: NavController) {
    val repository = remember { DonationRepository() }
    val coroutineScope = rememberCoroutineScope()
    var donations by remember { mutableStateOf<List<Donation>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val result = repository.getDonations() // ✅ Fixed: Ensure function exists
            donations = result ?: emptyList()
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Available Donations") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(donations) { donation ->
                        DonationItem(donation) {
                            // Future: Navigate to donation details screen if needed
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DonationItem(donation: Donation, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Item: ${donation.itemName}", style = MaterialTheme.typography.h6)
            Text(text = "Donor: ${donation.donorName}")
            Text(text = "Quantity: ${donation.quantity}")
            Text(text = "Location: ${donation.location}")
        }
    }
}
