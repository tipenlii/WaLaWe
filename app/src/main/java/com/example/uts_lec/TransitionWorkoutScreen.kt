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
fun TransitionWorkoutScreen(navController: NavController, exerciseNumber: Int, workoutName: String) {
    // Gunakan exerciseNumber untuk menentukan urutan latihan (first, second, etc.)
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
            .background(Color.White) // Menambahkan latar belakang putih
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Teks dinamis untuk latihan keberapa
            Text(
                text = exerciseTitle,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Pesan yang bisa disesuaikan berdasarkan nama latihan
            Text(
                text = "Get ready for your $workoutName! Let's get started!",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.Gray
            )

            // Gambar transisi
            Image(
                painter = painterResource(id = R.drawable.penguin_transition), // Sesuaikan gambar transisi
                contentDescription = "Penguin Exercise",
                modifier = Modifier.size(400.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol untuk memulai workout
            Button(
                onClick = {
                    // Navigasi ke WorkoutScreen dengan parameter yang sesuai
                    navController.navigate("workout_screen/$exerciseNumber/$workoutName")
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
