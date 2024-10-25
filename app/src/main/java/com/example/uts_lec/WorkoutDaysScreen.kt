package com.example.uts_lec

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

@Composable
fun WorkoutDaysScreen(navController: NavController, category: String) {
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    var completedDays by rememberSaveable { mutableStateOf(savedStateHandle?.get("completedDays") ?: 1) } // Hari yang sudah selesai, default Day 1 terbuka

    // Gunakan Box untuk menampilkan background image dan konten utama
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background_workout), // Ganti dengan gambar background Anda
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Konten utama (judul, daftar hari, dsb.)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Teks Title untuk kategori olahraga yang dinamis berdasarkan argumen category
            Text(
                text = "$category Workout",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Memastikan teks berada di tengah secara horizontal
                    .padding(top = 72.dp, bottom = 16.dp) // Menambahkan padding top dan bottom
            )

            // Daftar hari olahraga
            WorkoutDayItem(
                day = 1,
                isUnlocked = true, // Hari pertama selalu terbuka
                isCompleted = completedDays > 1, // Hari pertama dianggap selesai jika completedDays lebih dari 1
                description = "Ready to build a strong foundation for your $category muscles? This beginner-friendly $category workout is perfect for those just starting their fitness journey.",
                onStartClick = {
                    navController.navigate("workout_detail_screen/1/$category") // Navigasi ke workout detail screen untuk Day 1
                }
            )

            WorkoutDayItem(
                day = 2,
                isUnlocked = completedDays >= 2, // Terbuka jika hari 1 sudah selesai
                isCompleted = completedDays > 2,
                description = "This beginner $category workout is perfect if you're looking to improve both your strength and posture.",
                onStartClick = {
                    if (completedDays >= 2) {
                        navController.navigate("workout_detail_screen/2/$category") // Navigasi ke workout detail screen untuk Day 2
                    }
                }
            )

            WorkoutDayItem(
                day = 3,
                isUnlocked = completedDays >= 3, // Terbuka jika hari 2 sudah selesai
                isCompleted = completedDays > 3,
                description = "No gym? No problem! This beginner $category workout can be done anywhere, with no equipment needed.",
                onStartClick = {
                    if (completedDays >= 3) {
                        navController.navigate("workout_detail_screen/3/$category") // Navigasi ke workout detail screen untuk Day 3
                    }
                }
            )

            WorkoutDayItem(
                day = 4,
                isUnlocked = completedDays >= 4, // Terbuka jika hari 3 sudah selesai
                isCompleted = completedDays > 4,
                description = "No gym? No problem! This beginner $category workout can be done anywhere, with no equipment needed.",
                onStartClick = {
                    if (completedDays >= 4) {
                        navController.navigate("workout_detail_screen/4/$category") // Navigasi ke workout detail screen untuk Day 4
                    }
                }
            )
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
                    textAlign = TextAlign.Justify // Menambahkan alignment justify
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
                    // Menampilkan ikon terkunci jika hari masih terkunci
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
