package com.example.uts_lec

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import com.example.uts_lec.ui.theme.UTS_LECTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(navController: NavController) {
    val fullName = remember { mutableStateOf("") }
    val mobileNumber = remember { mutableStateOf("") }
    val dateOfBirth = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf(0) }
    val height = remember { mutableStateOf(0) }
    val buttonBlue = colorResource(id = R.color.button_blue)

    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(userId) {
        if (userId != null) {
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null) {
                    fullName.value = document.getString("name") ?: ""
                    mobileNumber.value = document.getString("mobileNumber") ?: ""
                    dateOfBirth.value = document.getString("dateOfBirth") ?: ""
                    weight.value = document.getLong("weight")?.toInt() ?: 0
                    height.value = document.getLong("height")?.toInt() ?: 0
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable { navController.navigateUp() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow), // Replace with your drawable resource
                contentDescription = "Back",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add some space between the icon and the text
            Text(
                text = "Update Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = buttonBlue
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        RoundedInputField(
            value = fullName.value,
            onValueChange = { fullName.value = it },
            label = "Full Name",
            iconResId = R.drawable.pen, // Replace with your drawable resource
            onIconClick = { /* Handle icon click */ },
            placeholderText = "Enter New Full Name"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedInputField(
            value = mobileNumber.value,
            onValueChange = { mobileNumber.value = it },
            label = "Mobile Number",
            iconResId = R.drawable.pen, // Replace with your drawable resource
            onIconClick = { /* Handle icon click */ },
            placeholderText = "Enter New Mobile Number"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedInputField(
            value = dateOfBirth.value,
            onValueChange = { dateOfBirth.value = it },
            label = "Date of Birth",
            iconResId = R.drawable.pen, // Replace with your drawable resource
            onIconClick = { /* Handle icon click */ },
            placeholderText = "Enter New Date of Birth"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedInputField(
            value = weight.value.toString(),
            onValueChange = { weight.value = it.toIntOrNull() ?: 0 },
            label = "Weight (kg)",
            iconResId = R.drawable.pen, // Replace with your drawable resource
            onIconClick = { /* Handle icon click */ },
            placeholderText = "Enter New Weight"
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedInputField(
            value = height.value.toString(),
            onValueChange = { height.value = it.toIntOrNull() ?: 0 },
            label = "Height (m)",
            iconResId = R.drawable.pen, // Replace with your drawable resource
            onIconClick = { /* Handle icon click */ },
            placeholderText = "Enter New Height"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (userId != null) {
                    val userUpdates = hashMapOf(
                        "name" to fullName.value,
                        "mobileNumber" to mobileNumber.value,
                        "dateOfBirth" to dateOfBirth.value,
                        "weight" to weight.value,
                        "height" to height.value
                    )
                    db.collection("users").document(userId)
                        .set(userUpdates, SetOptions.merge())
                        .addOnSuccessListener {
                            navController.navigateUp()
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Save Changes", fontSize = 16.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    iconResId: Int,
    onIconClick: () -> Unit,
    placeholderText: String
) {
    val buttonBlue = colorResource(id = R.color.button_blue)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = buttonBlue,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(24.dp),
            placeholder = {
                Text(
                    text = placeholderText, // Change this text based on the field
                    color = Color.Gray
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = onIconClick)
                        .padding(8.dp),
                    tint = Color.Unspecified
                )
            }
        )
    }
}

@Preview(showBackground = true, name = "Update Profile Preview")
@Composable
fun PreviewUpdateProfileScreen() {
    val navController = rememberNavController()
    UTS_LECTheme {
        UpdateProfileScreen(navController)
    }
}