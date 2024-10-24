// SetupNavGraph.kt
package com.example.uts_lec

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start_up_screen") {
        composable("start_up_screen") { StartUpScreen(navController = navController) }
        composable("onboarding_screen") { OnboardingScreen(navController = navController) }
        composable("sign_in") { SignInScreen(navController = navController) }
        composable("sign_up") { SignUpScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController) }
        composable("Umur") { GenderAndAgeSelectionScreen(navController = navController) }
        composable("Badan") {HeightAndWeightSelectionScreen(navController = navController) }
        composable("Goal") {GoalSelectionScreen(navController = navController) }
        composable("Perkenalan") {IntroductionScreen(navController = navController) }
    }
}
