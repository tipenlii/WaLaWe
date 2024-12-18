package com.example.uts_lec

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch

@Composable
fun ConfirmDoneScreen(navController: NavController, day: Int, category: String) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val coroutineScope = rememberCoroutineScope()

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

            ContinueButton(onClick = {
                if (userId != null) {
                    val userUpdates = hashMapOf(
                        "completedDays" to day + 1
                    )
                    db.collection("users").document(userId)
                        .set(userUpdates, SetOptions.merge())
                        .addOnSuccessListener {
                            // Save history data
                            val historyData = hashMapOf(
                                "day" to day,
                                "category" to category,
                                "timestamp" to System.currentTimeMillis(),
                                "reps" to 0, // Replace with actual reps
                                "duration" to 0 // Replace with actual duration
                            )
                            db.collection("users").document(userId).collection("history")
                                .add(historyData)
                                .addOnSuccessListener {
                                    navController.navigate("workout_days_screen/$category") {
                                        popUpTo("workout_days_screen/$category") { inclusive = true }
                                    }
                                }
                        }
                }
            })
        }
    }
}
