package com.example.uts_lec

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TutorialScreen(navController: NavController, exerciseNumber: Int, category: String) {
    val db = FirebaseFirestore.getInstance()
    val workout = remember { mutableStateOf<Workout?>(null) }
    val workoutDesc = remember { mutableStateOf<String?>(null) } // Untuk menyimpan deskripsi
    val isLoading = remember { mutableStateOf(true) }

    // Mengambil data workout berdasarkan kategori dan nomor latihan dari Firestore
    LaunchedEffect(exerciseNumber) {
        db.collection("workouts")
            .document("WeightLossBeginner")
            .collection(category)
            .document("workout_$exerciseNumber") // Mengambil dokumen berdasarkan nomor latihan
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val reps = documentSnapshot.getLong("reps")?.toInt()
                    val duration = documentSnapshot.getLong("duration")?.toInt()
                    val desc = documentSnapshot.getString("desc") // Ambil deskripsi
                    workout.value = Workout(
                        exerciseNumber = exerciseNumber,
                        name = documentSnapshot.getString("name") ?: "Unknown",
                        reps = reps,
                        duration = duration,
                        imageRes = getImageResourceId(documentSnapshot.getString("imageRes") ?: ""),
                    )

                    workoutDesc.value = desc // Simpan deskripsi yang diambil
                }
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
            }
    }

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
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                workout.value?.let { w ->
                    // Teks Nama Gerakan
                    Text(
                        text = w.name,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A74DA)
                    )

                    // Gambar Gerakan
                    Image(
                        painter = painterResource(id = w.imageRes),
                        contentDescription = w.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(400.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Deskripsi Gerakan jika ada
                    workoutDesc.value?.let { description ->
                        Card(
                            backgroundColor = Color(0xFFE3F2FD),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = description,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(16.dp),
                                color = Color(0xFF0A74DA),
                                textAlign = TextAlign.Justify
                            )
                        }
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
    }
}