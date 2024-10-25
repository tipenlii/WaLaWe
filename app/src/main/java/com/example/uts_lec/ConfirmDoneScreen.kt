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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun ConfirmDoneScreen(navController: NavController, day: Int, category: String) {
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
            // Teks "Good Job"
            Text(
                text = "Good Job!",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Gambar yang menunjukkan selesai
            Image(
                painter = painterResource(id = R.drawable.penguin_start2),
                contentDescription = "Completed",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Teks "Completed Exercise Day X"
            Text(
                text = "Completed Exercise Day $day",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol "Continue" untuk kembali ke WorkoutDaysScreen
            Button(
                onClick = {
                    // Perbarui status completedDays
                    navController.previousBackStackEntry?.savedStateHandle?.set("completedDays", day + 1)
                    // Kembali ke WorkoutDaysScreen dengan parameter kategori
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
