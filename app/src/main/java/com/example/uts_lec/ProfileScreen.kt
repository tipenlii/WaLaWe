package com.example.uts_lec

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.uts_lec.ui.theme.UTS_LECTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val primaryBlue = colorResource(id = R.color.primary_blue)
    val secondaryBlue = colorResource(id = R.color.secondary_blue)
    val buttonBlue = colorResource(id = R.color.button_blue)
    val textBlue = colorResource(id = R.color.text_blue)

    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userWeight by remember { mutableStateOf(0) }
    var userAge by remember { mutableStateOf(0) }
    var userHeight by remember { mutableStateOf(0) }
    var userBirthday by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val storage = FirebaseStorage.getInstance()

    LaunchedEffect(userId) {
        if (userId != null) {
            db.collection("users").document(userId).get().addOnSuccessListener { document ->
                if (document != null) {
                    userName = document.getString("name") ?: ""
                    userEmail = document.getString("email") ?: ""
                    userWeight = document.getLong("weight")?.toInt() ?: 0
                    userAge = document.getLong("age")?.toInt() ?: 0
                    userHeight = document.getLong("height")?.toInt() ?: 0
                    userBirthday = document.getString("dateOfBirth") ?: ""
                    profileImageUrl = document.getString("profileImageUrl") ?: ""
                }
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                uploadImageToFirebase(uri, userId, storage, db) { newImageUrl ->
                    profileImageUrl = newImageUrl
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp) // Adjust padding to avoid overlap with the navigation bar
        ) {
            TopAppBar(
                title = { Text("Back", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = buttonBlue) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "Back",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(textBlue, RoundedCornerShape(30.dp))
                        .width(350.dp)
                        .height(300.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "My Profile",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = profileImageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Transparent, CircleShape)
                            .clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        userName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        userEmail,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Birthday: $userBirthday",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(60.dp))
                }

                Row(
                    modifier = Modifier
                        .width(260.dp)
                        .padding(top = 280.dp)
                        .background(buttonBlue, shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth(0.9f)
                        .height(65.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileStat("$userWeight kg", "Weight")
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .padding(vertical = 8.dp),
                        color = Color.White
                    )
                    ProfileStat("$userAge", "Years Old")
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .padding(vertical = 8.dp),
                        color = Color.White
                    )
                    ProfileStat("$userHeight m", "Height")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            ProfileOptionItem("Profile", Icons.Default.Person, onClick = { navController.navigate("updateProfile") }, isExpanded = false)
            ProfileOptionItem("History", Icons.Default.History, onClick = { /* Navigate to History */ }, isExpanded = false)
            ProfileOptionItem("Privacy Policy", Icons.Default.PrivacyTip, onClick = { /* Navigate to Privacy Policy */ }, isExpanded = false)
            ProfileOptionItem("Settings", Icons.Default.Settings, onClick = { navController.navigate("settings") }, isExpanded = false)
            ProfileOptionItem("Logout", Icons.Default.ExitToApp, onClick = { /* Handle Logout */ }, isExpanded = false)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            BottomNavigationBar(
                currentPage = "profile",
                onItemSelected = { page ->
                    when (page) {
                        "home" -> navController.navigate("home")
                        "profile" -> navController.navigate("profile")
                        "statistics" -> navController.navigate("statistics")
                    }
                },
                navController = navController,
                icons = listOf(
                    painterResource(id = R.drawable.home_icon),
                    painterResource(id = R.drawable.statistics_icon),
                    painterResource(id = R.drawable.profile_icon)
                )
            )
        }
    }
}
@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val navController = rememberNavController()
    UTS_LECTheme {
        ProfileScreen(navController = navController)
    }
}

suspend fun uploadImageToFirebase(
    uri: Uri,
    userId: String?,
    storage: FirebaseStorage,
    db: FirebaseFirestore,
    onSuccess: (String) -> Unit
) {
    if (userId == null) return

    val storageRef = storage.reference.child("profile_images/$userId.jpg")
    storageRef.putFile(uri).await()
    val downloadUrl = storageRef.downloadUrl.await().toString()

    db.collection("users").document(userId).update("profileImageUrl", downloadUrl).await()
    onSuccess(downloadUrl)
}