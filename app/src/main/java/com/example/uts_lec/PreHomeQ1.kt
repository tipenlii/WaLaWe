package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@Composable
fun GenderAndAgeSelectionScreen(navController: NavHostController) {
    var selectedGender by remember { mutableStateOf("Girl") }
    var selectedAge by remember { mutableStateOf(17) }
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

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
            Spacer(modifier = Modifier.height(180.dp))

            GenderSelection(selectedGender = selectedGender, onGenderSelected = { gender ->
                selectedGender = gender
            })

            AgeSelection(selectedAge = selectedAge, onAgeSelected = { age ->
                selectedAge = age
            })

            // Pushes the button to the bottom

            ContinueButton(onClick = {
                if (userId != null) {
                    val userUpdates = hashMapOf(
                        "gender" to selectedGender,
                        "age" to selectedAge
                    )
                    db.collection("users").document(userId)
                        .set(userUpdates, SetOptions.merge())
                        .addOnSuccessListener {
                            navController.navigate("Badan")
                        }
                }
            })
        }
    }
}