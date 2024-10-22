// StartUpScreen.kt
package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect

@Composable
fun StartUpScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your logo
            contentDescription = "App Logo",
            modifier = Modifier.size(250.dp)
        )

        // Move to the onboarding screen after 3 seconds
        LaunchedEffect(Unit) {
            delay(2000L)
            navController.navigate("onboarding_screen")
        }
    }
}
