package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HistoryMoveScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val history = remember { mutableStateOf<List<WorkoutHistory>>(emptyList()) }
    val workouts = remember { mutableStateOf<List<Workout>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    // Fetch history data from Firestore
    LaunchedEffect(userId) {
        if (userId != null) {
            db.collection("users").document(userId).collection("history")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val historyList = querySnapshot.documents.map { document ->
                        WorkoutHistory(
                            day = document.getLong("day")?.toInt() ?: 0,
                            category = document.getString("category") ?: "Unknown",
                            timestamp = document.getLong("timestamp") ?: 0L,
                            reps = document.getLong("reps")?.toInt() ?: 0,
                            duration = document.getLong("duration")?.toInt() ?: 0,
                            imageUrl = document.getString("imageUrl") ?: "" // Fetch imageUrl
                        )
                    }
                    history.value = historyList

                    // Fetch workout data for each category
                    historyList.forEach { historyItem ->
                        db.collection("workouts")
                            .document("WeightLossBeginner")
                            .collection(historyItem.category)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                if (!querySnapshot.isEmpty) {
                                    val workoutList = querySnapshot.documents.mapIndexed { index, document ->
                                        val reps = document.getLong("reps")?.toInt()
                                        val duration = document.getLong("duration")?.toInt()
                                        val calories = document.getLong("calories")?.toInt() ?: 0

                                        Workout(
                                            exerciseNumber = index + 1,
                                            name = document.getString("name") ?: "Unknown",
                                            reps = reps,
                                            duration = duration,
                                            imageRes = getImageResourceId(document.getString("imageRes") ?: ""),
                                        )
                                    }
                                    workouts.value = workoutList
                                }
                                isLoading.value = false
                            }
                            .addOnFailureListener {
                                isLoading.value = false
                            }
                    }
                }
        }
    }

    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(history.value) { historyItem ->
                    HistoryItem(historyItem)
                }
            }
        }
    )
}

@Composable
private fun HistoryItem(history: WorkoutHistory) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp) // Adjust the width of the card
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = getImageResourceId(history.imageUrl)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Adjust the height of the image
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width((history.reps * 1.5).dp) // Adjust the width of the progress bar
                        .background(Color.Yellow, shape = RoundedCornerShape(8.dp))
                ) {}
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Duration: ${history.duration} seconds", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

data class WorkoutHistory(
    val day: Int,
    val category: String,
    val timestamp: Long,
    val reps: Int,
    val duration: Int,
    val imageUrl: String // Add imageUrl field
)