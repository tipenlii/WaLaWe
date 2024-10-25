package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import kotlinx.coroutines.delay

@Composable
fun WorkoutScreen(navController: NavController, workout: Workout) {
    // State untuk countdown dan timer utama
    var countdown by remember { mutableStateOf(3) }
    var workoutTimer by remember { mutableStateOf(workout.duration) } // Menggunakan durasi workout
    var workoutStarted by remember { mutableStateOf(false) }
    var workoutFinished by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = workoutStarted) {
        if (workoutStarted) {
            // Mulai countdown 3 detik
            while (countdown > 0) {
                delay(1000L)
                countdown--
            }

            // Setelah countdown, mulai timer workout
            while (workoutTimer > 0) {
                delay(1000L)
                workoutTimer--
            }

            // Setelah workout selesai
            workoutFinished = true
        }
    }

    // Tampilan utama workout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Latar belakang putih
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Teks Title (Exercise Number dan Workout Name)
            Text(
                text = "Exercise 0${workout.exerciseNumber}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA)
            )

            Text(
                text = workout.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Gambar Workout
            Image(
                painter = painterResource(id = workout.imageRes),
                contentDescription = workout.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Countdown atau timer workout ditampilkan di sini
            if (countdown > 0) {
                // Tampilkan countdown 3 detik
                Text(
                    text = countdown.toString(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A74DA)
                )
            } else {
                // Tampilkan timer workout
                Text(
                    text = "${workout.reps.toUpperCase()}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = String.format("%02d:%02d", workoutTimer / 60, workoutTimer % 60),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (!workoutStarted) {
                // Tombol "Start" sebelum countdown dimulai
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

            if (workoutFinished) {
                // Tombol "Done" setelah workout selesai
                Button(
                    onClick = {
                        navController.popBackStack() // Kembali ke layar sebelumnya atau layar lain
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
    }
}
