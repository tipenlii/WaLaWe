package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
fun TutorialScreen(navController: NavController, exerciseNumber: Int) {
    // List latihan yang sama seperti di WorkoutDetailScreen
    val workouts = listOf(
        Workout(
            exerciseNumber = 1,
            name = "Push Up",
            reps = "20 x Reps",
            imageRes = R.drawable.pushup,
            duration = 120 // 2 menit
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

    // Ambil detail latihan berdasarkan exerciseNumber
    val workout = workouts.firstOrNull { it.exerciseNumber == exerciseNumber } ?: workouts[0]

    // Gunakan Box untuk latar belakang warna putih
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Warna latar belakang putih
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Teks Nama Gerakan
            Text(
                text = workout.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA)
            )

            // Gambar Gerakan
            Image(
                painter = painterResource(id = workout.imageRes),
                contentDescription = workout.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(400.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deskripsi Gerakan
            Card(
                backgroundColor = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Beginner ${workout.name} Guide\n\n" +
                            "Start in a position... [Tambahkan deskripsi lengkap gerakan di sini]",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF0A74DA)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Tombol untuk kembali ke Workout Detail Screen
            Button(
                onClick = { navController.navigateUp() }, // Kembali ke layar sebelumnya
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0A74DA),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Back", fontSize = 16.sp)
            }
        }
    }
}
