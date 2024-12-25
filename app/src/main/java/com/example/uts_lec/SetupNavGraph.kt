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
        composable("Badan") { HeightAndWeightSelectionScreen(navController = navController) }
        composable("Goal") { GoalSelectionScreen(navController = navController) }
        composable("Perkenalan") { IntroductionScreen(navController = navController) }
        composable("workout") { WorkoutCategoryScreen(navController = navController) }
        composable("workout_detail_screen/{day}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: 1
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            WorkoutDetailScreen(navController = navController, day = day, category = category)
        }
        composable("workout_days_screen/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            category?.let {
                WorkoutDaysScreen(navController = navController, category = it)
            }
        }
        composable("tutorial_screen/{exerciseNumber}/{category}") { backStackEntry ->
            val exerciseNumber = backStackEntry.arguments?.getString("exerciseNumber")?.toInt() ?: 1
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            TutorialScreen(navController = navController, exerciseNumber = exerciseNumber, category = category)
        }
        composable("transition_screen/{day}/{exerciseNumber}/{workoutName}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toInt() ?: 1
            val exerciseNumber = backStackEntry.arguments?.getString("exerciseNumber")?.toInt() ?: 1
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            val workoutName = backStackEntry.arguments?.getString("workoutName") ?: "Unknown"
            TransitionWorkoutScreen(
                navController = navController,
                day = day,
                exerciseNumber = exerciseNumber,
                category = category,
                workoutName = workoutName
            )
        }
        composable("workout_screen/{day}/{exerciseNumber}/{name}/{reps}/{imageRes}/{duration}/{category}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day")?.toIntOrNull() ?: 1
            val exerciseNumber = backStackEntry.arguments?.getString("exerciseNumber")?.toInt() ?: 1
            val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
            val reps = backStackEntry.arguments?.getString("reps")?.toIntOrNull() ?: 0
            val imageName = backStackEntry.arguments?.getString("imageRes") ?: "default_image"
            val duration = backStackEntry.arguments?.getString("duration")?.toIntOrNull() ?: 0
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            val imageRes = getImageResourceId(imageName)
            val workout = Workout(
                exerciseNumber = exerciseNumber,
                name = name,
                reps = reps,
                imageRes = imageRes,
                duration = duration
            )
            WorkoutScreen(navController = navController, workout = workout, day = day, category = category, exerciseNumber = exerciseNumber)
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
        composable("statistics") { StatisticsScreen(navController) }
        composable("history_move") { HistoryMoveScreen(navController = navController) }
    }
}
