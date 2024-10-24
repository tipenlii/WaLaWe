package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun GenderAndAgeSelectionScreen(navController: NavHostController) {
    var selectedGender by remember { mutableStateOf("Girl") }
    var selectedAge by remember { mutableStateOf(17) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        // Top background image
        Image(
            painter = painterResource(id = R.drawable.atas_prehome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.TopCenter)
        )



        // "Gender" text
        Text(
            text = "Gender",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold), // Make text bold
            color = Color(0xFF4A90E2),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp) // Adjust padding as needed
        )

        // Bottom background image
        Image(
            painter = painterResource(id = R.drawable.bawah_prehome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(180.dp)) // Kurangi height spacer ini untuk mengurangi jarak antara gender dan age

            GenderSelection(selectedGender = selectedGender, onGenderSelected = { gender ->
                selectedGender = gender
            })

            AgeSelection(selectedAge = selectedAge, onAgeSelected = { age ->
                selectedAge = age
            })

            ContinueButton(onClick = {
                navController.navigate("Badan") // Action on continue button pressed
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenderAndAgeSelectionScreen() {
    GenderAndAgeSelectionScreen(navController = rememberNavController())
}