package com.example.uts_lec

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uts_lec.ui.theme.UTS_LECTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var isNotificationExpanded by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    "Settings",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notification Settings
        ProfileOptionItem(
            "Notification Setting",
            Icons.Default.Notifications,
            onClick = { isNotificationExpanded = !isNotificationExpanded },
            isExpanded = isNotificationExpanded
        )

        if (isNotificationExpanded) {
            SettingSwitch("General Notification")
            SettingSwitch("Sound")
            SettingSwitch("Do Not Disturb Mode")
            SettingSwitch("Vibrate")
            SettingSwitch("Lock Screen")
            SettingSwitch("Reminders")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Setting and Delete Account
        ProfileOptionItem(
            "Password Setting",
            Icons.Default.Lock,
            onClick = { /* Navigate to Password Screen */ }
        )

        ProfileOptionItem(
            "Delete Account",
            Icons.Default.Delete,
            onClick = { showDeleteConfirmation = true }
        )

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Account") },
                text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        showDeleteConfirmation = false
                        // Handle account deletion logic here
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun SettingSwitch(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
        Switch(
            checked = true,
            onCheckedChange = { /* Handle change */ },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Preview(showBackground = true, name = "Setting Screen Preview")
@Composable
fun SettingScreenPreview() {
    val navController = rememberNavController()
    UTS_LECTheme {
        SettingsScreen(navController)
    }
}
