package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@Composable
fun GoalSelectionScreen(navController: NavHostController) {
    var selectedGoal by remember { mutableStateOf("Lose Weight") }
    var selectedDifficulty by remember { mutableStateOf("Beginner") }
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

        // "Goal" text
        Text(
            text = "Goal",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF11579D),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Goal selection
            Text(
                text = "Select Your Goal",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RadioButtonGroup(
                options = listOf("Lose Weight", "Build Muscle", "Improve Endurance"),
                selectedOption = selectedGoal,
                onOptionSelected = { goal -> selectedGoal = goal }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Difficulty selection
            Text(
                text = "Select Difficulty Level",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RadioButtonGroup(
                options = listOf("Beginner", "Intermediate", "Advanced"),
                selectedOption = selectedDifficulty,
                onOptionSelected = { difficulty -> selectedDifficulty = difficulty }
            )

            Spacer(modifier = Modifier.height(32.dp))

                ContinueButton(onClick = {
                    if (userId != null) {
                        val userUpdates = hashMapOf(
                            "goal" to selectedGoal,
                            "difficulty" to selectedDifficulty
                        )
                        db.collection("users").document(userId)
                            .set(userUpdates, SetOptions.merge())
                            .addOnSuccessListener {
                                navController.navigate("Perkenalan")
                            }
                    }
                })
        }
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(options) { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp)
            ) {
                Text(
                    text = option,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (option == selectedOption) Color.Black else Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoalSelectionScreen() {
    GoalSelectionScreen(navController = rememberNavController())
}