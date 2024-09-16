package com.example.cgs_pos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


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