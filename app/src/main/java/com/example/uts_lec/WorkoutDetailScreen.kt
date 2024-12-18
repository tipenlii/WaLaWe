package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WorkoutDetailScreen(navController: NavController, day: Int, category: String) {
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
            .fillMaxSize() // Mengisi seluruh ukuran layar
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Menggunakan seluruh ukuran vertikal
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Day $day List",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Menggunakan sisa ruang
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp) // Memberikan ruang bawah pada LazyColumn
                ) {
                    itemsIndexed(workouts.value) { index, workout ->
                        WorkoutCard(workout, onClick = {
                            navController.navigate("tutorial_screen/${workout.exerciseNumber}/${category}")
                        })
                    }
                }
            }

            Button(
                onClick = {
                    val currentWorkoutIndex = 0
                    if (currentWorkoutIndex < workouts.value.size) {
                        val workout = workouts.value[currentWorkoutIndex]
                        navController.navigate("transition_screen/$day/${currentWorkoutIndex + 1}/${workout.name}/$category")
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

@Composable
fun WorkoutCard(workout: Workout, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
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

                // Tampilkan reps jika ada, jika tidak tampilkan duration
                workout.reps?.let {
                    Text(text = "$it Reps", fontSize = 14.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
                }

                workout.duration?.let {
                    Text(text = "$it  Sec", fontSize = 14.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
data class Workout(
    val exerciseNumber: Int,  // Nomor urutan latihan
    val name: String,         // Nama latihan (misalnya, Push Up)
    val reps: Int?,           // Jumlah repetisi (misalnya, 20 x Reps) atau null jika menggunakan duration
    val duration: Int?,       // Durasi latihan dalam detik (misalnya, 120 detik) atau null jika menggunakan reps
    val imageRes: Int         // Resource ID untuk gambar latihan
)

