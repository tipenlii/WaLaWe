package com.example.uts_lec

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.uts_lec.R

@Composable
fun SignUpScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    // Ukuran input field dan tombol
    val inputWidth = 350.dp

    // Box untuk menumpuk background image dan konten lainnya
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Gambar sebagai background
        Image(
            painter = painterResource(id = R.drawable.background_signin), // Ganti dengan background Anda
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Mengisi seluruh layar dengan gambar
        )

        // Konten Sign Up di atas background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp),  // Jarak dari atas
            verticalArrangement = Arrangement.Top, // Memulai dari atas layar
            horizontalAlignment = Alignment.CenterHorizontally // Memusatkan konten secara horizontal
        ) {
            // Teks Sign Up di bagian atas
            Text(
                text = "Sign Up",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA), // Warna biru untuk teks
                modifier = Modifier.padding(bottom = 32.dp) // Jarak dari teks ke form
            )

            // Name input field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = false // Reset error saat mulai mengetik
                },
                label = { Text("Full Name") },
                isError = nameError,
                modifier = Modifier
                    .width(inputWidth)
                    .background(Color.White, RoundedCornerShape(16.dp))
            )
            if (nameError) {
                Text(
                    text = "Name cannot be empty",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false // Reset error saat mulai mengetik
                },
                label = { Text("Email Address") },
                isError = emailError,
                modifier = Modifier
                    .width(inputWidth)
                    .background(Color.White, RoundedCornerShape(16.dp))
            )
            if (emailError) {
                Text(
                    text = "Email cannot be empty",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Password input field (dengan tipe password)
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false // Reset error saat mulai mengetik
                },
                label = { Text("Password") },
                isError = passwordError,
                visualTransformation = PasswordVisualTransformation(), // Tipe password untuk menyembunyikan input
                modifier = Modifier
                    .width(inputWidth)
                    .background(Color.White, RoundedCornerShape(16.dp))
            )
            if (passwordError) {
                Text(
                    text = "Password cannot be empty",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password input field (dengan tipe password)
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = false // Reset error saat mulai mengetik
                },
                label = { Text("Confirm Password") },
                isError = confirmPasswordError,
                visualTransformation = PasswordVisualTransformation(), // Tipe password untuk menyembunyikan input
                modifier = Modifier
                    .width(inputWidth)
                    .background(Color.White, RoundedCornerShape(16.dp))
            )
            if (confirmPasswordError) {
                Text(
                    text = "Confirm Password cannot be empty",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start).padding(start = 32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Button dengan warna biru
            Button(
                onClick = {
                    // Validasi input kosong
                    if (name.isEmpty()) {
                        nameError = true
                    }
                    if (email.isEmpty()) {
                        emailError = true
                    }
                    if (password.isEmpty()) {
                        passwordError = true
                    }
                    if (confirmPassword.isEmpty()) {
                        confirmPasswordError = true
                    }

                    if (!nameError && !emailError && !passwordError && !confirmPasswordError) {
                        // Periksa apakah password cocok
                        if (password == confirmPassword) {
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Menyimpan data ke Firestore
                                        val db = FirebaseFirestore.getInstance()
                                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                                        val user = hashMapOf(
                                            "name" to name,
                                            "email" to email,
                                            "uid" to userId
                                        )
                                        db.collection("users")
                                            .document(userId ?: "")
                                            .set(user)
                                            .addOnSuccessListener {
                                                navController.navigate("sign_in")
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(navController.context, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                            }
                                    } else {
                                        Toast.makeText(navController.context, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(navController.context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(navController.context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(inputWidth),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0A74DA),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}
