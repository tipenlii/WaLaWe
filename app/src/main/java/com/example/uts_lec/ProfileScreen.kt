package com.example.uts_lec

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.uts_lec.ui.theme.UTS_LECTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import com.example.uts_lec.MainActivity
import android.graphics.Bitmap

@Composable
fun showImagePickerDialog(
    mainActivity: MainActivity,
    galleryLauncher: ActivityResultLauncher<String>,
    cameraLauncher: ActivityResultLauncher<Uri>,
    tempImageUriState: MutableState<Uri?>,
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Image") },
        text = { Text("Choose an option to set your profile picture.") },
        confirmButton = {
            // Launch gallery picker
            TextButton(onClick = {
                galleryLauncher.launch("image/*")
                onDismiss()
            }) {
                Text("Gallery")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                // Handle permissions and camera
                val tempFile = createTempImageFile(context)
                val tempUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    tempFile
                )
                tempImageUriState.value = tempUri // Update the state with the URI
                cameraLauncher.launch(tempUri)
                onDismiss()
            }) {
                Text("Camera")
            }
        }
    )
}

fun createTempImageFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "temp_image", /* prefix */
        ".jpg",       /* suffix */
        storageDir    /* directory */
    )
}

fun hasPermissions(context: Context): Boolean {
    val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
    val storagePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return cameraPermission == PackageManager.PERMISSION_GRANTED &&
            storagePermission == PackageManager.PERMISSION_GRANTED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val buttonBlue = colorResource(id = R.color.button_blue)
    val textBlue = colorResource(id = R.color.text_blue)
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userWeight by remember { mutableIntStateOf(0) }
    var userAge by remember { mutableIntStateOf(0) }
    var userHeight by remember { mutableIntStateOf(0) }
    var userBirthday by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }
    val storage = FirebaseStorage.getInstance()
    val tempImageUriState = remember { mutableStateOf<Uri?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                try {
                    uploadImageToFirebase(uri, userId, storage, db) { newImageUrl ->
                        profileImageUrl = newImageUrl
                    }
                } catch (e: Exception) {
                    Log.e("ProfileScreen", "Error uploading image", e)
                }
            }
        }
    }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempImageUriState.value != null) {
            coroutineScope.launch {
                try {
                    uploadImageToFirebase(tempImageUriState.value!!, userId, storage, db) { newImageUrl ->
                        profileImageUrl = newImageUrl
                    }
                } catch (e: Exception) {
                    Log.e("ProfileScreen", "Error uploading camera image", e)
                }
            }
        }
    }

    if (isDialogVisible) {
        showImagePickerDialog(
            mainActivity = LocalContext.current as MainActivity,
            galleryLauncher = galleryLauncher,
            cameraLauncher = cameraLauncher,
            tempImageUriState = tempImageUriState,
            context = context,
            onDismiss = { isDialogVisible = false }
        )
    }

    LaunchedEffect(userId) {
        userId?.let {
            try {
                db.collection("users").document(it).get().addOnSuccessListener { document ->
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
            } catch (e: Exception) {
                Log.e("ProfileScreen", "Error fetching user data", e)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {
            TopAppBar(
                title = { Text("Profile", fontSize = 20.sp, color = buttonBlue) },
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
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        if (profileImageUrl.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.profiledef),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Transparent, CircleShape)
                                    .clickable { isDialogVisible = true },
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                model = profileImageUrl,
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .clickable { isDialogVisible = true },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        userName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(userEmail, fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary)
                    Text(
                        "Birthday: $userBirthday",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            ProfileOptionItem("Profile", Icons.Default.Person, onClick = { navController.navigate("updateProfile") }, isExpanded = false)
            ProfileOptionItem("History", Icons.Default.History, onClick = { navController.navigate("history_category_screen") }, isExpanded = false)
            ProfileOptionItem("Privacy Policy", Icons.Default.PrivacyTip, onClick = { /* Navigate to Privacy Policy */ }, isExpanded = false)
            ProfileOptionItem("Settings", Icons.Default.Settings, onClick = { navController.navigate("settings") }, isExpanded = false)
            ProfileOptionItem("Logout", Icons.Default.ExitToApp, onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("sign_in") {
                    popUpTo("sign_in") { inclusive = true }
                }
            }, isExpanded = false)
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

suspend fun uploadBitmapToFirebase(
    bitmap: Bitmap,
    userId: String?,
    storage: FirebaseStorage
): String? {
    val storageRef = storage.reference.child("profile_pictures/${userId}.jpg")
    val baos = java.io.ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val data = baos.toByteArray()
    return storageRef.putBytes(data).await().storage.downloadUrl.await().toString()
}