// MainActivity.kt
package com.example.uts_lec


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi Firebase jika belum diinisialisasi
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        setContent {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
        }
    }
}
