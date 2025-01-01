package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun StartUpScreen(navController: NavHostController) {
    // State untuk melacak status navigasi  
    var navigationDecided by remember { mutableStateOf(false) }

    // LaunchedEffect untuk memeriksa status login  
    LaunchedEffect(Unit) {
        // Tambahkan delay singkat untuk menampilkan splash screen  
        delay(2000L)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // Pengguna sudah login, periksa kelengkapan profil  
            try {
                val userId = currentUser.uid
                val userDoc = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .get()
                    .await()

                // Periksa apakah profil lengkap  
                if (checkProfileCompletion(userDoc)) {
                    // Jika profil lengkap, langsung ke home  
                    navController.navigate("home") {
                        popUpTo("startup_screen") { inclusive = true }
                    }
                } else {
                    // Jika profil tidak lengkap, arahkan ke halaman pengisian profil  
                    navController.navigate("umur") {
                        popUpTo("startup_screen") { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                // Jika gagal memeriksa, arahkan ke onboarding  
                navController.navigate("onboarding_screen") {
                    popUpTo("startup_screen") { inclusive = true }
                }
            }
        } else {
            // Pengguna belum login, arahkan ke onboarding  
            navController.navigate("onboarding_screen") {
                popUpTo("startup_screen") { inclusive = true }
            }
        }

        navigationDecided = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your logo  
            contentDescription = "App Logo",
            modifier = Modifier.size(250.dp)
        )
    }
}

// Fungsi untuk memeriksa kelengkapan profil (sama seperti sebelumnya)  
fun checkProfileCompletion(documentSnapshot: com.google.firebase.firestore.DocumentSnapshot): Boolean {
    // Daftar field yang harus diisi  
    val requiredFields = listOf(
        "classification",
        "age",
        "gender",
        "goal",
        "height",
        "weight"
    )

    // Periksa apakah semua field ada dan tidak null  
    return requiredFields.all { field ->
        documentSnapshot.contains(field) && documentSnapshot[field] != null
    }
}