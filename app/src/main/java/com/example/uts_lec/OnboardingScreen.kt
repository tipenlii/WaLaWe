// OnboardingScreen.kt
package com.example.uts_lec

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Menyebarkan konten vertikal
    ) {
        // Bagian yang menampilkan halaman onboarding
        HorizontalPager(
            count = 3, // Number of onboarding pages
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> OnboardingPageWithBackgroundOnly(
                    backgroundImage = R.drawable.background_start1
                )
                1 -> OnboardingPageWithImageAndText(
                    imageRes = R.drawable.penguin_start2,
                    text = "Stay Fit, Stay Strong, Stay Motivated."
                )
                2 -> OnboardingPageWithBackgroundOnly(
                    backgroundImage = R.drawable.background_start3
                )
            }
        }

        // Menampilkan Pager Indicator atau Button pada halaman terakhir
        if (pagerState.currentPage == 2) {
            // Halaman terakhir menampilkan button
            Button(
                onClick = { navController.navigate("sign_in") },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0A74DA), // Warna latar belakang biru
                    contentColor = Color.White // Warna teks putih
                )
            ) {
                Text(text = "Get Started", fontWeight = FontWeight.Bold)
            }
        } else {
            // Menampilkan indikator titik di bawah halaman selain halaman terakhir
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                activeColor = Color(0xFF0A74DA), // Warna titik aktif
                inactiveColor = Color.Gray // Warna titik tidak aktif
            )
        }
    }
}