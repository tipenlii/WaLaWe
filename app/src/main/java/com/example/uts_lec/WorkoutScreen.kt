package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun WorkoutScreen(navController: NavController, workout: Workout, day: Int, exerciseNumber: Int, category: String) {
    val db = FirebaseFirestore.getInstance()
    val workoutState = remember { mutableStateOf<Workout?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    var countdown by remember { mutableStateOf(3) }
    var workoutTimer by remember { mutableStateOf(workout.duration ?: 0) }
    var workoutStarted by remember { mutableStateOf(false) }
    var workoutFinished by remember { mutableStateOf(false) }

    // Mengambil data workout dari Firestore
    LaunchedEffect(exerciseNumber) {
        db.collection("workouts")
            .document("WeightLossBeginner") // Bisa disesuaikan dengan kategori latihan
            .collection(category)
            .document("workout_$exerciseNumber") // Mengambil dokumen berdasarkan nomor latihan
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val name = documentSnapshot.getString("name") ?: "Unknown"
                    val reps = documentSnapshot.getLong("reps")?.toInt()
                    val duration = documentSnapshot.getLong("duration")?.toInt() ?: 0
                    val imageRes = getImageResourceId(documentSnapshot.getString("imageRes") ?: "")

                    workoutState.value = Workout(
                        exerciseNumber = exerciseNumber,
                        name = name,
                        reps = reps,
                        duration = duration,
                        imageRes = imageRes
                    )
                }
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
            }
    }

    // Handle countdown logic and timer
    LaunchedEffect(workoutStarted) {
        if (workoutStarted) {
            // Start with countdown timer of 3 seconds
            if (countdown > 0) {
                while (countdown > 0) {
                    delay(1000L)
                    countdown -= 1
                }
            }

            // After countdown, start workout timer
            if (workoutTimer > 0) {
                while (workoutTimer > 0) {
                    delay(1000L)  // Delay 1 second
                    workoutTimer -= 1
                }
                workoutFinished = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading.value) {
                // Display loading indicator while data is being fetched
                CircularProgressIndicator()
            } else {
                workoutState.value?.let { currentWorkout ->
                    // Display workout exercise number and name
                    Text(
                        text = "Exercise 0${currentWorkout.exerciseNumber}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A74DA)
                    )

                    Text(
                        text = currentWorkout.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A74DA),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Display workout image
                    Image(
                        painter = painterResource(id = currentWorkout.imageRes),
                        contentDescription = currentWorkout.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Countdown or workout timer
                    if (countdown > 0) {
                        // Show countdown (3 seconds before workout starts)
                        Text(
                            text = countdown.toString(),
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0A74DA)
                        )
                    } else {
                        // Show reps and workout timer after countdown finishes
                        currentWorkout.reps?.let {
                            if (it > 0) {
                                Text(
                                    text = "Reps: $it",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Show workout timer in MM:SS format
                        if (workoutTimer > 0) {
                            Text(
                                text = String.format("%02d:%02d", workoutTimer / 60, workoutTimer % 60),
                                fontSize = 18.sp
                            )
                        } else {
                            // Show "Done" button after the timer reaches 0
                            Button(
                                onClick = {
                                    if (currentWorkout.exerciseNumber < 10) { // Adjust according to total exercises
                                        // Navigate to the next workout
                                        navController.navigate("transition_screen/$day/${currentWorkout.exerciseNumber + 1}/${currentWorkout.name}/$category")
                                    } else {
                                        // If all workouts are completed, navigate to the completion screen
                                        navController.navigate("confirm_done_screen/$day/$category")
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF0A74DA),
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Done", fontSize = 16.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    if (!workoutStarted) {
                        // Show "Start" button before countdown begins
                        Button(
                            onClick = { workoutStarted = true },
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
        }
    }
}
