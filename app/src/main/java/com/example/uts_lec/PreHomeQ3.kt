package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun GoalSelectionScreen(navController: NavHostController) {
    var selectedGoal by remember { mutableStateOf("") }
    var selectedActivityLevel by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.bawah_prehome3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
        )
        // "Goal" and "Physical Activity Level" selection
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Goal",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D),
                modifier = Modifier.padding(top = 18.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Radio buttons for Goal selection
            RadioButtonGroup(
                options = listOf("Lose Weight", "Gain Weight", "Muscle Mass Gain", "Shape Body", "Others"),
                selectedOption = selectedGoal,
                onOptionSelected = {
                    selectedGoal = it
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Physical Activity Level",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D),
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centering the text
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons for selecting activity level
            ActivityLevelSelection(selectedActivityLevel) {
                selectedActivityLevel = it
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Continue Button at the bottom
            ContinueButton(onClick = {
                    navController.navigate("Perkenalan")

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
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp)
            ) {
                Text(
                    text = option,
                    color = Color(0xFF1A73E8),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1A73E8))
                )
            }
        }
    }
}

@Composable
fun ActivityLevelSelection(selectedLevel: String, onLevelSelected: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        listOf("Beginner", "Intermediate", "Advance").forEach { level ->
            Button(
                onClick = {
                    onLevelSelected(level)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedLevel == level) Color(0xFF1A73E8) else Color(0xFF11579D)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(40.dp)
                    .width(130.dp)
            ) {
                Text(
                    text = level,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
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