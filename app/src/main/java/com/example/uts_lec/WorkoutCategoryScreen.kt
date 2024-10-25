package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun WorkoutCategoryScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("") } // State untuk melacak kategori yang dipilih

    // Konten utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Teks Title
        Text(
            text = "Category",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 72.dp),
            color = Color(0xFF0A74DA) // Warna biru
        )

        // Teks deskripsi
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Choose one of the focused exercises",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            color = Color(0xFF0A74DA) // Warna biru
        )

        // Membuat 2 baris dengan 2 kolom (4 kotak)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Baris pertama (Leg dan Strength)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WorkoutCard(
                    title = "Leg Workout",
                    time = "23 min remaining",
                    imageRes = R.drawable.leg_workout,
                    isSelected = selectedCategory == "Leg", // Tanda apakah card dipilih
                    onClick = { selectedCategory = "Leg" },  // Set kategori terpilih
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                WorkoutCard(
                    title = "Strength Exercise",
                    time = "23 min remaining",
                    imageRes = R.drawable.strength_workout,
                    isSelected = selectedCategory == "Strength", // Tanda apakah card dipilih
                    onClick = { selectedCategory = "Strength" }, // Set kategori terpilih
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Baris kedua (Chest dan Abs)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WorkoutCard(
                    title = "Chest Workout",
                    time = "15 min remaining",
                    imageRes = R.drawable.chest_workout,
                    isSelected = selectedCategory == "Chest", // Tanda apakah card dipilih
                    onClick = { selectedCategory = "Chest" }, // Set kategori terpilih
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                WorkoutCard(
                    title = "Abs Workout",
                    time = "15 min remaining",
                    imageRes = R.drawable.abs_workout,
                    isSelected = selectedCategory == "Abs", // Tanda apakah card dipilih
                    onClick = { selectedCategory = "Abs" }, // Set kategori terpilih
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Button untuk melanjutkan ke halaman detail workout
        Button(
            onClick = {
                if (selectedCategory.isNotEmpty()) {
                    navController.navigate("workout_days_screen/$selectedCategory")
                }
            },
            modifier = Modifier
                .width(150.dp)
                .height(45.dp),
            enabled = selectedCategory.isNotEmpty(), // Aktifkan tombol hanya ketika ada kategori yang dipilih
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF0A74DA), // Warna biru
                contentColor = Color.White
            )
        ) {
            Text(text = "Continue", fontSize = 14.sp)
        }
    }
}

@Composable
fun WorkoutCard(
    title: String,
    time: String,
    imageRes: Int,
    isSelected: Boolean, // Parameter untuk menentukan apakah card dipilih
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = if (isSelected) Color.Gray else Color.White, // Ganti warna background jika dipilih
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() } // Set kartu terpilih saat diklik
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = title, fontSize = 16.sp)
            Text(text = time, fontSize = 12.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f))
        }
    }
}
