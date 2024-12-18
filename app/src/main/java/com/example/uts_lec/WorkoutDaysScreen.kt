package com.example.uts_lec

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WorkoutDaysScreen(navController: NavController, category: String) {
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    var completedDays by rememberSaveable { mutableStateOf(1) } // Default Day 1 terbuka
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Mengambil data completedDays dari Firestore
    LaunchedEffect(userId) {
        if (userId != null) {
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null) {
                    completedDays = document.getLong("completedDays")?.toInt() ?: 1
                    Log.d("WorkoutDaysScreen", "CompletedDays updated to $completedDays")
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_workout),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "$category Workout",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 72.dp, bottom = 16.dp)
            )

            // Daftar Workout Days
            for (day in 1..4) {
                WorkoutDayItem(
                    day = day,
                    isUnlocked = completedDays >= day,
                    isCompleted = completedDays > day,
                    description = when (day) {
                        1 -> "Ready to build a strong foundation for your $category muscles? This beginner-friendly $category workout is perfect for those just starting their fitness journey."
                        2 -> "This beginner $category workout is perfect if you're looking to improve both your strength and posture."
                        else -> "No gym? No problem! This beginner $category workout can be done anywhere, with no equipment needed."
                    },
                    onStartClick = {
                        if (completedDays >= day) {
                            navController.navigate("workout_detail_screen/$day/$category")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun WorkoutDayItem(
    day: Int,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    description: String,
    onStartClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = when (day) {
                    1 -> R.drawable.day1
                    2 -> R.drawable.day2
                    3 -> R.drawable.day3
                    else -> R.drawable.day4
                }),
                contentDescription = "Day $day",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Day $day", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                // Deskripsi teks dengan justify alignment
                Text(
                    text = description,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp),
                    textAlign = TextAlign.Justify
                )

                // Tombol "Start Now" atau tanda "Done"
                if (isUnlocked) {
                    if (isCompleted) {
                        Text(
                            text = "Done",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    } else {
                        Button(
                            onClick = onStartClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0A74DA), contentColor = Color.White),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(text = "Start Now")
                        }
                    }
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Locked",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
