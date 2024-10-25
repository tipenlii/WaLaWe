package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.*

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        val calendar = Calendar.getInstance()
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val currentYear = calendar.get(Calendar.YEAR)
        val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")

        // Calculate the dates of the current week
        val today = Calendar.getInstance()
        val datesOfWeek = (0..6).map {
            val day = Calendar.getInstance().apply {
                time = today.time
                add(Calendar.DAY_OF_MONTH, it - today.get(Calendar.DAY_OF_WEEK) + 2) // Adjusting to Monday start
            }
            day.get(Calendar.DAY_OF_MONTH)
        }

        // Progress for penguin image (adjust this based on your app logic)
        val daysProgress = remember { mutableStateOf(10) } // Example: 45th day of workout
        val penguinResId = when {
            daysProgress.value < 20 -> R.drawable.penguin_1 // Replace with actual penguin images
            daysProgress.value < 40 -> R.drawable.penguin_2
            daysProgress.value < 60 -> R.drawable.penguin_3
            daysProgress.value < 80 -> R.drawable.penguin_4
            else -> R.drawable.penguin_5
        }

        // Top background image
        Image(
            painter = painterResource(id = R.drawable.backhome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(75.dp))

            // Display Month and Year
            Text(
                text = "$monthName $currentYear",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Calendar Days (M, T, W...)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 13.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }

            // Calendar Dates (real-time)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                datesOfWeek.forEachIndexed { index, date ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (index == today.get(Calendar.DAY_OF_WEEK) - 2) Color(0xFF11579D) else Color.Transparent
                            )
                            .size(25.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$date",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White // Highlight current day
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Penguin Progress Image
            Image(
                painter = painterResource(id = penguinResId),
                contentDescription = "Penguin Progress",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Small green progress bar with fish icon
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(7.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width((daysProgress.value * 2.5).dp) // Adjust width based on your logic (max 100 days = 250.dp)
                            .background(Color.Green)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Overview Section
            OverviewSection()

            Spacer(modifier = Modifier.height(30.dp))

            // Motivational Card
            MotivationalCard(navController = navController)
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            BottomNavigationBar(
                currentPage = "Home",
                onItemSelected = { /* Handle navigation item selection */ },
                icons = listOf(
                    painterResource(id = R.drawable.home_icon),
                    painterResource(id = R.drawable.statistics_icon),
                    painterResource(id = R.drawable.profile_icon)
                ),
                navController = navController
            )
        }
    }
}

@Composable
fun OverviewSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF11579D),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OverviewCard("0", "Cal Burnt", R.drawable.cal_burnt_icon)
            OverviewCard("0h", "Total Time", R.drawable.total_time_icon)
            OverviewCard("0", "Exercises", R.drawable.exercises_icon)
        }
    }
}

@Composable
fun OverviewCard(value: String, title: String, iconResId: Int) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start, // Mengatur teks agar rata kiri
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(30.dp) // Mengurangi ukuran ikon
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun MotivationalCard(navController: NavController) {
    Box(
        modifier = Modifier
            .width(450.dp)
            .height(140.dp)
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.motivational),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Content on top of the background image
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Let’s crush those\nworkouts together!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate("workout") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Start now", color = Color(0xFF11579D))
                }
            }
            Image(
                painter = painterResource(id = R.drawable.penguinhapi),
                contentDescription = "Penguin Image",
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}