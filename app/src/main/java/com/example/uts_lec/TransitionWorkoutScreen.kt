package com.example.uts_lec

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TransitionWorkoutScreen(navController: NavController, day: Int, exerciseNumber: Int, workoutName: String, category: String) {
    val db = FirebaseFirestore.getInstance()
    val workout = remember { mutableStateOf<Workout?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    // Mengambil data workout berdasarkan kategori dan nomor latihan dari Firestore
    LaunchedEffect(exerciseNumber) {
        db.collection("workouts")
            .document("WeightLossBeginner")
            .collection(category)  // Kategori seperti "Abs", "Arm", "Leg", "Chest"
            .document("workout_$exerciseNumber") // Mengambil dokumen berdasarkan workout_1, workout_2, dll.
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val reps = documentSnapshot.getLong("reps")?.toInt() // Menggunakan string reps
                    val duration = documentSnapshot.getLong("duration")?.toInt()

                    workout.value = Workout(
                        exerciseNumber = exerciseNumber,
                        name = documentSnapshot.getString("name") ?: "Unknown",
                        reps = reps,
                        duration = duration,
                        imageRes = getImageResourceId(documentSnapshot.getString("imageRes") ?: "")
                    )
                }
                isLoading.value = false
                Log.d("TransitionWorkoutScreen", "Data berhasil dimuat: ${workout.value}")
            }
            .addOnFailureListener {
                isLoading.value = false
                Log.e("TransitionWorkoutScreen", "Gagal memuat data workout", it)
            }
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
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                workout.value?.let { currentWorkout ->

                    // Display the exercise title (updated to support up to the tenth exercise)
                    val exerciseTitle = when (exerciseNumber) {
                        1 -> "Your First Exercise"
                        2 -> "Your Second Exercise"
                        3 -> "Your Third Exercise"
                        4 -> "Your Fourth Exercise"
                        5 -> "Your Fifth Exercise"
                        6 -> "Your Sixth Exercise"
                        7 -> "Your Seventh Exercise"
                        8 -> "Your Eighth Exercise"
                        9 -> "Your Ninth Exercise"
                        10 -> "Your Tenth Exercise"
                        else -> "Your Exercise"
                    }

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
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    // Display the transition image (Penguin or other image)
                    Image(
                        painter = painterResource(id = R.drawable.penguin_transition), // Replace with your actual image resource
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
    }
}
