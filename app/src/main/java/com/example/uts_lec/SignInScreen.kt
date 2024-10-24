package com.example.uts_lec

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.example.uts_lec.R

@Composable
fun SignInScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    // Box untuk menumpuk background image dan konten lainnya
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Gambar sebagai background
        Image(
            painter = painterResource(id = R.drawable.background_signin), // Ganti dengan image Anda
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Mengisi seluruh layar
        )

        // Konten Sign In di atas background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 72.dp),
            verticalArrangement = Arrangement.Top, // Untuk memulai dari bagian atas
            horizontalAlignment = Alignment.CenterHorizontally // Memusatkan konten secara horizontal
        ) {
            // Teks Sign In di bagian atas
            Text(
                text = "Sign In",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0A74DA), // Sesuaikan dengan warna yang diinginkan
                modifier = Modifier.padding(top = 32.dp) // Jarak dari bagian atas
            )
            Spacer(modifier = Modifier.height(32.dp)) // Jarak antara teks dan input form

            // Gunakan width yang sama untuk input dan button
            val inputWidth = 350.dp // Menentukan ukuran yang konsisten untuk input dan tombol

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false // Menghapus error saat pengguna mulai mengetik
                },
                label = { Text("Email Address") },
                isError = emailError, // Menandai error jika input kosong
                modifier = Modifier
                    .width(inputWidth)
                    .background(Color.White, RoundedCornerShape(16.dp)) // Menambahkan background putih pada input
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

            // Password input field dengan visual transformation untuk menyembunyikan input
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false // Menghapus error saat pengguna mulai mengetik
                },
                label = { Text("Password") },
                isError = passwordError, // Menandai error jika input kosong
                visualTransformation = PasswordVisualTransformation(), // Menyembunyikan input
                modifier = Modifier
                    .width(inputWidth)
                    .background(Color.White, RoundedCornerShape(8.dp)) // Menambahkan background putih pada input
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

            // Sign In Button dengan ukuran yang sama dan warna biru
            Button(
                onClick = {
                    // Validasi input kosong
                    if (email.isEmpty()) {
                        emailError = true
                    }
                    if (password.isEmpty()) {
                        passwordError = true
                    }

                    if (!emailError && !passwordError) {
                        // Jika tidak ada error, lakukan sign in
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("umur")
                                } else {
                                    Toast.makeText(navController.context, "Sign In Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(navController.context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(inputWidth),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0A74DA), // Warna biru
                    contentColor = Color.White // Warna teks putih
                )
            ) {
                Text(text = "Sign In")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigasi ke halaman Sign Up
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val annotatedText = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                        append("Create Account")
                    }
                }

                Text(
                    text = annotatedText,
                    modifier = Modifier.clickable {
                        navController.navigate("sign_up")
                    },
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
