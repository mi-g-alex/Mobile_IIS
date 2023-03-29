package by.g_alex.mobile_iis.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.g_alex.mobile_iis.presentation.login_screen.LoginScreen
import by.g_alex.mobile_iis.presentation.profile_screen.ProfileCVScreen

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.ProfileScreen.route
        ) {
            composable(
                route = Screen.LoginScreen.route
            ) {
                LoginScreen(navController = navController)
            }
            composable(
                route = Screen.ProfileScreen.route,
            ) {
                ProfileCVScreen(navController = navController)

            }

        }
    }
}