package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenderSelection(selectedGender: String, onGenderSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Girl Button
        GenderOption(
            gender = "Girl",
            imageRes = R.drawable.girl_image, // Gambar penguin "Girl"
            isSelected = selectedGender == "Girl",
            onGenderSelected = onGenderSelected
        )
        // Boy Button
        GenderOption(
            gender = "Boy",
            imageRes = R.drawable.boy_image, // Gambar penguin "Boy"
            isSelected = selectedGender == "Boy",
            onGenderSelected = onGenderSelected
        )
    }
}

@Composable
fun GenderOption(gender: String, imageRes: Int, isSelected: Boolean, onGenderSelected: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .clickable { onGenderSelected(gender) }
    ) {
        Text(
            text = gender,
            style = TextStyle(
                color = if (gender == "Girl") Color(0xFFFF6B6B) else Color(0xFF4A90E2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold // Make text bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .size(180.dp) // Set size for the box
                .clip(RoundedCornerShape(24.dp)) // Rounded corners for the box
                .background(if (isSelected) Color(0xFF80BBFF) else Color(0xFFF5F5F5)) // Change background color based on selection // Change background color based on selection
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize() // Fill the available space in the box
                    .clip(RoundedCornerShape(20.dp)) // Add rounded corners to the image itself
            )
        }
    }
}
