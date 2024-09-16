package com.example.cgs_pos

import PasscodeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

// Sealed class for destinations
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Passcode : Screen("passcode")
    data object Dashboard : Screen("dashboard")
}

@Composable
fun MyApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = { navController.navigate(Screen.Passcode.route) })
        }
        composable(Screen.Passcode.route) {
            PasscodeScreen(navController = navController)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val delayDuration = 2000L

    LaunchedEffect(Unit) {
        delay(delayDuration)
        onTimeout()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon for Splash Screen
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your icon resource
            contentDescription = null,
            modifier = Modifier.size(120.dp) // Adjust size as needed
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Text for Splash Screen
        Text(
            text = "CGS POS",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    MyApp(navController = navController)
                }
            }
        }
    }
}
