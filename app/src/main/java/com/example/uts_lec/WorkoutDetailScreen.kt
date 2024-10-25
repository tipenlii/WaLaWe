package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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

@Composable
fun WorkoutDetailScreen(navController: NavController, day: Int, category: String) {
    val workouts = listOf(
        Workout(
            exerciseNumber = 1,
            name = "Push Up",
            reps = "20 x Reps",
            imageRes = R.drawable.pushup,
            duration = 120 // 2 menit atau disesuaikan dengan waktu yang diinginkan
        ),
        Workout(
            exerciseNumber = 2,
            name = "Plank",
            reps = "1 Min",
            imageRes = R.drawable.plank,
            duration = 60 // 1 menit
        ),
        Workout(
            exerciseNumber = 3,
            name = "Both Side Plank",
            reps = "1 Min",
            imageRes = R.drawable.sideplank,
            duration = 60 // 1 menit
        ),
        Workout(
            exerciseNumber = 4,
            name = "Abs Workout",
            reps = "16 x Reps",
            imageRes = R.drawable.abs,
            duration = 90 // Durasi bisa disesuaikan
        ),
        Workout(
            exerciseNumber = 5,
            name = "Torso and Trap Workout",
            reps = "8 x Reps",
            imageRes = R.drawable.torsoandtraps,
            duration = 60 // Durasi bisa disesuaikan
        ),
        Workout(
            exerciseNumber = 6,
            name = "Lower Back Exercise",
            reps = "14 x Reps",
            imageRes = R.drawable.lowerback,
            duration = 80 // Durasi bisa disesuaikan
        )
    )

    val currentWorkoutIndex = remember { mutableStateOf(0) }

    // Gunakan Box untuk latar belakang warna putih
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Menetapkan latar belakang putih
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Teks Title
            Text(
                text = "Day $day List",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            // List Workout Card
            workouts.forEach { workout ->
                WorkoutCard(workout, onClick = {
                    // Navigasi ke TutorialScreen dengan detail gerakan
                    navController.navigate("tutorial_screen/${workout.name}")
                })
            }

            Spacer(modifier = Modifier.weight(1f))

            // Tombol Continue
            Button(
                onClick = {
                    if (currentWorkoutIndex.value < workouts.size) {
                        val workout = workouts[currentWorkoutIndex.value]
                        // Menambahkan parameter day ke dalam navigasi
                        navController.navigate("transition_screen/$day/${currentWorkoutIndex.value + 1}/${workout.name}/$category")
                        currentWorkoutIndex.value++ // Lanjutkan ke latihan berikutnya
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
                Text(text = workout.reps, fontSize = 14.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
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
    val reps: String,         // Jumlah repetisi atau durasi (misalnya, 20 x Reps)
    val imageRes: Int,        // Resource ID untuk gambar latihan
    val duration: Int         // Durasi latihan dalam detik (misalnya, 120 detik)
)
