package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TransitionWorkoutScreen(navController: NavController, day: Int, exerciseNumber: Int, workoutName: String, category: String) {
    // Define the list of workouts
    val workouts = listOf(
        Workout(1, "Push Up", "20 x Reps", R.drawable.pushup, 5),
        Workout(2, "Plank", "1 Min", R.drawable.plank, 5),
        Workout(3, "Both Side Plank", "1 Min", R.drawable.sideplank, 5),
        Workout(4, "Abs Workout", "16 x Reps", R.drawable.abs, 5),
        Workout(5, "Torso and Trap Workout", "8 x Reps", R.drawable.torsoandtraps, 5),
        Workout(6, "Lower Back Exercise", "14 x Reps", R.drawable.lowerback, 5)
    )

    // Get the current workout based on exerciseNumber
    val currentWorkout = workouts.getOrNull(exerciseNumber - 1) ?: workouts[0] // Default to first workout if not found

    // Define the title for the exercise
    val exerciseTitle = when (exerciseNumber) {
        1 -> "Your First Exercise"
        2 -> "Your Second Exercise"
        3 -> "Your Third Exercise"
        4 -> "Your Fourth Exercise"
        5 -> "Your Fifth Exercise"
        6 -> "Your Sixth Exercise"
        else -> "Your Exercise"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Add a white background
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display the exercise title
            Text(
                text = exerciseTitle,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display a message indicating the workout
            Text(
                text = "Get ready for your ${currentWorkout.name}! Let's get started!",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.Gray
            )

            // Display the transition image
            Image(
                painter = painterResource(id = R.drawable.penguin_transition),
                contentDescription = "Penguin Exercise",
                modifier = Modifier.size(400.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Button to start the workout
            Button(
                onClick = {
                    // Navigate to the workout screen with the correct workout details and day parameter
                    navController.navigate("workout_screen/$day/${currentWorkout.exerciseNumber}/${currentWorkout.name}/${currentWorkout.reps}/${currentWorkout.imageRes}/${currentWorkout.duration}/$category")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0A74DA),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Start", fontSize = 16.sp)
            }
        }
    }
}
