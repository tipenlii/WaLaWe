package com.example.uts_lec

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@Composable
fun ConfirmDoneScreen(navController: NavController, day: Int, category: String) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val currentCalories = remember { mutableStateOf(0) }
    val totalCalories = remember { mutableStateOf(0) }
    val currentDuration = remember { mutableStateOf(0) }
    val totalDuration = remember { mutableStateOf(0) }
    val workoutIndex = remember { mutableStateOf(0) }
    val isLoading = remember { mutableStateOf(true) }
    val totalCaloriesBurned = remember { mutableStateOf(0) }
    val totalWorkoutDuration = remember { mutableStateOf(0) }
    val workoutDB = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())

    // Fetch current calories for the specific date and total calories
    LaunchedEffect(userId) {
        if (userId != null) {
            // Fetch total calories and duration for the user
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        totalCalories.value = document.getLong("Calories")?.toInt() ?: 0
                        totalDuration.value = document.getLong("duration")?.toInt() ?: 0
                        workoutDB.value = document.getLong("exercise")?.toInt() ?: 0

                    }
                }
            // Fetch calories and duration for the specific day
            db.collection("users").document(userId)
                .collection("CaloriesPerDay").document(currentDate)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        currentCalories.value = document.getLong("Calories")?.toInt() ?: 0
                        currentDuration.value = document.getLong("duration")?.toInt() ?: 0
                    }
                    isLoading.value = false
                }
                .addOnFailureListener {
                    Log.e("FirestoreError", "Error fetching data: ${it.message}")
                    isLoading.value = false
                }
        }
    }
    LaunchedEffect(category) {
        db.collection("workouts")
            .document("WeightLossBeginner")
            .collection(category)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    totalCaloriesBurned.value = querySnapshot.documents.sumOf { document ->
                        document.getLong("Calories")?.toInt() ?: 0
                    }
                    totalWorkoutDuration.value = querySnapshot.documents.sumOf { document ->
                        document.getLong("duration")?.toInt() ?: 0
                    }
                    currentCalories.value += totalCaloriesBurned.value
                    currentDuration.value += totalWorkoutDuration.value
                    workoutIndex.value = querySnapshot.size()
                }
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading.value) {
            CircularProgressIndicator()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Good Job!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A74DA),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.penguin_start2),
                    contentDescription = "Completed",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Completed Exercise Day $day",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A74DA)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Workouts Completed: ${workoutIndex.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A74DA)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (userId != null) {
                            // Save totalCalories and totalDuration to the user's main document
                            db.collection("users").document(userId)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document != null && document.exists()) {
                                        val newTotalCalories = totalCalories.value +  currentCalories.value
                                        val newTotalDuration = totalDuration.value + currentDuration.value

                                        db.collection("users").document(userId)
                                            .set(mapOf("Calories" to newTotalCalories, "duration" to newTotalDuration), SetOptions.merge())
                                            .addOnSuccessListener {
                                                Log.d("FirestoreSuccess", "Total calories and duration updated successfully")
                                            }
                                            .addOnFailureListener {
                                                Log.e("FirestoreError", "Error updating total calories and duration: ${it.message}")
                                            }
                                    }
                                }
                                .addOnFailureListener {
                                    Log.e("FirestoreError", "Error fetching current total values: ${it.message}")
                                }

                            // Save currentCalories and currentDuration to the CaloriesPerDay sub-collection
                            db.collection("users").document(userId)
                                .collection("CaloriesPerDay").document(currentDate)
                                .set(mapOf("Calories" to currentCalories.value, "duration" to currentDuration.value, "exercise" to workoutIndex.value), SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.d("FirestoreSuccess", "Today's calories and duration updated successfully")
                                }
                                .addOnFailureListener {
                                    Log.e("FirestoreError", "Error updating today's calories and duration: ${it.message}")
                                }

                            // Update completedDays status
                            db.collection("users").document(userId)
                                .update("completedDays", day + 1)
                                .addOnSuccessListener {
                                    Log.d("FirestoreSuccess", "Completed days updated successfully")
                                }
                                .addOnFailureListener {
                                    Log.e("FirestoreError", "Error updating completed days: ${it.message}")
                                }
                            db.collection("users").document(userId)
                                .set(mapOf("exercise" to (workoutDB.value + workoutIndex.value)), SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.d("FirestoreSuccess", "Workout index updated successfully")
                                }
                                .addOnFailureListener {
                                    Log.e("FirestoreError", "Error updating workout index: ${it.message}")
                                }
                            db.collection("users").document(userId)
                                .collection("CaloriesPerDay").document(currentDate)
                                .set(mapOf("exercise" to workoutIndex.value), SetOptions.merge())
                                .addOnSuccessListener {
                                    Log.d("FirestoreSuccess", "Workout index updated successfully")
                                }
                                .addOnFailureListener {
                                    Log.e("FirestoreError", "Error updating workout index: ${it.message}")
                                }
                        }
                        // Navigate back to WorkoutDaysScreen with category parameter
                        navController.navigate("workout_days_screen/$category") {
                            popUpTo("workout_days_screen/$category") { inclusive = true }
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
                    Text(text = "Continue", fontSize = 16.sp)
                }
            }
        }
    }
}