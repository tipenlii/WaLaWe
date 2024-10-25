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
        composable("workout") { WorkoutCategoryScreen(navController = navController) }
        composable("workout_detail_screen/{day}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: 1
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            WorkoutDetailScreen(navController = navController, day = day, category = category)
        }
        composable("workout_days_screen/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            WorkoutDaysScreen(navController = navController, category = category ?: "Unknown")
        }
        composable("tutorial_screen/{exerciseNumber}") { backStackEntry ->
            val exerciseNumber = backStackEntry.arguments?.getString("exerciseNumber")?.toInt() ?: 1
            TutorialScreen(navController = navController, exerciseNumber = exerciseNumber)
        }
        composable("transition_screen/{day}/{exerciseNumber}/{workoutName}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toInt() ?: 1
            val exerciseNumber = backStackEntry.arguments?.getString("exerciseNumber")?.toInt() ?: 1
            val workoutName = backStackEntry.arguments?.getString("workoutName") ?: "Unknown"
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            TransitionWorkoutScreen(navController = navController, day = day, exerciseNumber = exerciseNumber, workoutName = workoutName, category = category)
        }

        composable("workout_screen/{day}/{exerciseNumber}/{name}/{reps}/{imageRes}/{duration}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: 1
            val exerciseNumber = backStackEntry.arguments?.getString("exerciseNumber")?.toInt() ?: 1
            val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
            val reps = backStackEntry.arguments?.getString("reps") ?: ""
            val imageRes = backStackEntry.arguments?.getString("imageRes")?.toInt() ?: R.drawable.chest_workout
            val duration = backStackEntry.arguments?.getString("duration")?.toInt() ?: 0
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"

            val workout = Workout(
                exerciseNumber = exerciseNumber,
                name = name,
                reps = reps,
                imageRes = imageRes,
                duration = duration
            )

            WorkoutScreen(navController = navController, workout = workout, day = day, category = category)
        }


        composable("confirm_done_screen/{day}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: 1
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            ConfirmDoneScreen(navController = navController, day = day, category = category)
        }

        composable("profile") { ProfileScreen(navController) }
        composable("updateProfile") { UpdateProfileScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("password") { PasswordScreen(navController) }
    }
}
