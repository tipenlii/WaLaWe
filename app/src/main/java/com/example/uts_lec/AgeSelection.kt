package com.example.uts_lec

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AgeSelection(selectedAge: Int, onAgeSelected: (Int) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Age",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF4A90E2)
        )
        Text(
            text = selectedAge.toString(),
            style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A90E2)),
        )
        Icon(
            imageVector = Icons.Default.ArrowDropUp,
            contentDescription = "Increase Age",
            modifier = Modifier.clickable {
                if (selectedAge < 100) {
                    val newAge = selectedAge + 1
                    onAgeSelected(newAge)
                    db.collection("users").document("user_id").update("age", newAge)
                }
            }
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items((1..80).toList()) { age ->
                Text(
                    text = age.toString(),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onAgeSelected(age)
                            db.collection("users").document("userId").update("age", age)
                        },
                    color = if (age == selectedAge) Color.White else Color(0xFF4A90E2),
                    fontWeight = if (age == selectedAge) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 24.sp
                )
            }
        }
    }
}