package com.rescueconnect.app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlertScreen(navController: NavController) {
    val alerts = remember { mutableStateListOf(
        "🔥 Wildfire warning in your area!",
        "🚨 Tornado alert issued for the region.",
        "⚠️ Heavy rainfall expected. Stay indoors!",
        "🚑 Emergency health advisory: COVID-19 precautions.",
        "🛑 Road closure due to landslide in northern district."
    ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Alerts", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(16.dp)
        ) {
            items(alerts) { alert ->
                AlertItem(alert)
            }
        }
    }
}

@Composable
fun AlertItem(alert: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 6.dp,
        backgroundColor = Color.White
    ) {
        Text(
            text = alert,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

