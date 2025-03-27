package com.rescueconnect.app.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rescueconnect.app.ui.screens.ChatScreen
import com.rescueconnect.app.ui.screens.LoginScreen
import com.rescueconnect.app.ui.screens.SignupScreen
import com.rescueconnect.app.ui.screens.HomeScreen // ✅ Corrected Import
import com.rescueconnect.app.ui.screens.DonateScreen
import com.rescueconnect.app.ui.screens.DonationListScreen
import com.rescueconnect.app.ui.screens.LocationSharingScreen
import com.rescueconnect.app.ui.screens.AlertScreen
import com.rescueconnect.app.ui.screens.HelpScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Donate : Screen("donate")
    object DonationList : Screen("donation_list")
    object Chat : Screen("chat")
    object Location : Screen("location")
    object Safety : Screen("safety")
    object Help : Screen("help")
    object Settings : Screen("settings")
    object Alert : Screen("alert")
}

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavigation(navController: NavHostController) {
    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(0) // Clear back stack
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Signup.route) { SignupScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Donate.route) { DonateScreen(navController) }
        composable(Screen.DonationList.route) { DonationListScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.Alert.route) {
            AlertScreen(navController)
        }
        composable(Screen.Help.route) { HelpScreen(navController) }


    }
}
