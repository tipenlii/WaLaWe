package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface


@Composable
fun HistoryWorkoutScreen(navController: NavController, category: String) {
    val db = FirebaseFirestore.getInstance()
    val workouts = remember { mutableStateOf<List<Workout>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    // Mengambil data workout berdasarkan kategori dari Firestore
    LaunchedEffect(category) {
        db.collection("workouts")
            .document("WeightLossBeginner")
            .collection(category)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val workoutList = querySnapshot.documents.mapIndexed { index, document ->
                        val reps = document.getLong("reps")?.toInt()
                        val duration = document.getLong("duration")?.toInt()

                        Workout(
                            exerciseNumber = index + 1,
                            name = document.getString("name") ?: "Unknown",
                            reps = reps,
                            duration = duration,
                            imageRes = getImageResourceId(document.getString("imageRes") ?: "")
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$category Workout History",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    itemsIndexed(workouts.value) { index, workout ->
                        HistoryWorkoutCard(workout)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryWorkoutCard(workout: Workout) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White, // Mengatur latar belakang
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = workout.imageRes),
                contentDescription = workout.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = workout.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                // Tampilkan progress bar berdasarkan reps atau duration
                workout.reps?.let {
                    ProgressBarWithLabel(value = it.toFloat(), maxValue = 100f) // Untuk Reps
                }

                workout.duration?.let {
                    ProgressBarWithLabel(value = it.toFloat(), maxValue = 120f) // Untuk Duration (dalam detik)
                }
            }
        }
    }
}

@Composable
fun ProgressBarWithLabel(value: Float, maxValue: Float) {
    val progress = value / maxValue // Progres berdasarkan nilai dan maxValue

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        // ProgressBar
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color(0xFF0A74DA),
            backgroundColor = Color.Gray.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        // Label untuk progres (menampilkan prosentase)
        Text(
            text = "${(progress * 100).toInt()}%",
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )
    }
}
