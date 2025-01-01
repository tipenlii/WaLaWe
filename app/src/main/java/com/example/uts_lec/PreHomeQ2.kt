package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun HeightAndWeightSelectionScreen(navController: NavHostController) {
    var height by remember { mutableStateOf(165) }
    var weight by remember { mutableStateOf(75) }
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) { // Set background color to white

        // Penguin drawable image in the background
        Image(
            painter = painterResource(id = R.drawable.penguinsetengah), // Drawable for the penguin
            contentDescription = "Penguin",
            modifier = Modifier
                .height(1050.dp)
                .align(Alignment.CenterStart) // Align the image to the left
        )

        // "What is Your Height & Weight?" text split into two lines and right-aligned
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 15.dp, top = 15.dp), // Add padding to the right
            horizontalAlignment = Alignment.End // Right-align text
        ) {
            Text(
                text = "What is Your",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D)
            )
            Text(
                text = "Height & Weight?",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF11579D)
            )
        }

        // Bottom background image
        Image(
            painter = painterResource(id = R.drawable.bawah_prehome1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
        )

        // Main content (Height and Weight)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center the content vertically
        ) {
            // Row for Height Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center, // Center the height selection
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(140.dp)) // Adjust space for the penguin image

                // Height value text and LazyColumn for height
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$height Cm", // Display current height in one line
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.height(200.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        items((150..200).toList()) { h ->
                            Text(
                                text = h.toString(),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        height = h
                                    },
                                color = if (h == height) Color.Black else Color(0xFF4A90E2),
                                fontWeight = if (h == height) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp)) // Adjust space for the penguin image
            }

            Spacer(modifier = Modifier.height(70.dp)) // Increase the spacing between height and weight sections

            // Weight section using LazyRow
            Text(
                text = "$weight Kg",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                items((50..100).toList()) { w ->
                    Text(
                        text = w.toString(),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                weight = w
                            },
                        color = if (w == weight) Color.Black else Color(0xFF4A90E2),
                        fontWeight = if (w == weight) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }

        // Add Continue Button at the bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ContinueButton(onClick = {
                if (userId != null) {
                    val userUpdates = hashMapOf(
                        "height" to height,
                        "weight" to weight
                    )
                    db.collection("users").document(userId)
                        .set(userUpdates, SetOptions.merge())
                        .addOnSuccessListener {
                            navController.navigate("Goal")
                        }
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeightAndWeightSelectionScreen() {
    HeightAndWeightSelectionScreen(navController = rememberNavController())
}