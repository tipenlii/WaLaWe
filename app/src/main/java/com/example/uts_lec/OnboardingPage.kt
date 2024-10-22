// OnboardingPage.kt
package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale

@Composable
fun OnboardingPageWithBackgroundOnly(backgroundImage: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Gambar sebagai background
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null, // Tidak memerlukan deskripsi konten karena tidak ada teks
            modifier = Modifier.fillMaxSize(), // Gambar memenuhi seluruh layar
            contentScale = ContentScale.Crop // Gambar dipotong sesuai layar tanpa distorsi
        )
    }
}

@Composable
fun OnboardingPageWithImageAndText(imageRes: Int, text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Memusatkan komponen secara horizontal
        verticalArrangement = Arrangement.Center // Memusatkan komponen secara vertikal
    ) {
        // Gambar di tengah
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null, // Tidak perlu deskripsi konten
            modifier = Modifier.size(250.dp) // Sesuaikan ukuran gambar
        )

        Spacer(modifier = Modifier.height(24.dp)) // Jarak antara gambar dan teks

        // Teks di bawah gambar
        Text(
            text = text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0A74DA), // Sesuaikan warna teks
            textAlign = TextAlign.Center // Menyelaraskan teks ke tengah
        )
    }
}

@Composable
fun OnboardingPage(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Memusatkan secara horizontal
        verticalArrangement = Arrangement.Center // Memusatkan secara vertikal
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(), // Memenuhi lebar penuh
            textAlign = TextAlign.Center // Teks di tengah
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(), // Memenuhi lebar penuh
            textAlign = TextAlign.Center // Teks di tengah
        )
    }
}
