package com.rescueconnect.app.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rescueconnect.app.R
import com.rescueconnect.app.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    tint = Color.Black
                )
            }
            Text(
                text = "Rescue Connect",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // Reduced extra space

        // 🔹 Lifesaver Logo
        Image(
            painter = painterResource(id = R.drawable.lifesaver),
            contentDescription = "Lifesaver Icon",
            modifier = Modifier.size(140.dp) // Slightly bigger
        )

        Spacer(modifier = Modifier.height(20.dp)) // Adjusted

        // 🔹 Emergency Alert Button (Now Navigates to Alert Screen)
        Button(
            onClick = { navController.navigate(Screen.Alert.route) }, // Navigates to AlertScreen
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(55.dp)
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD32F2F)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "Emergency Alert",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(35.dp)) // Pushed elements downward

        // 🔹 Feature Buttons Grid - Adjusted spacing to fill the screen better
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f) // Helps balance space
        ) {
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                FeatureButton(R.drawable.ic_help, "Help") { navController.navigate(Screen.Help.route) }
                FeatureButton(R.drawable.ic_donate, "Donate") { navController.navigate(Screen.Donate.route) }
                FeatureButton(R.drawable.ic_location, "Location") { navController.navigate(Screen.Location.route) }
            }
            Spacer(modifier = Modifier.height(25.dp)) // Increased
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                FeatureButton(R.drawable.ic_chat, "Community Chat") { navController.navigate(Screen.Chat.route) }
                FeatureButton(R.drawable.ic_first_aid, "First Aid") { navController.navigate(Screen.Safety.route) }
                FeatureButton(R.drawable.ic_logout, "Log Out") { /* Implement Logout Action */ }
            }
        }

        Spacer(modifier = Modifier.height(15.dp)) // Reduced bottom gap

        // 🔹 Bottom Info Text (Balances layout)
        Text(
            text = "Stay Safe, Help is Nearby.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 15.dp)
        )
    }
}

@Composable
fun FeatureButton(iconRes: Int, text: String, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    val buttonSize by animateDpAsState(if (isPressed) 75.dp else 70.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(buttonSize)
                .background(Color.White, shape = CircleShape)
                .shadow(5.dp, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
