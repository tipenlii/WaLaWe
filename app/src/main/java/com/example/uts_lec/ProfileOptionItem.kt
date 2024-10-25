// ProfileOptionItem.kt
package com.example.uts_lec

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.uts_lec.ui.theme.UTS_LECTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp

@Composable
fun ProfileOptionItem(name: String, icon: ImageVector, onClick: () -> Unit, isExpanded: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 70.dp)
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = name, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(name, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        Spacer(modifier = Modifier.weight(1f)) // Pushes the arrow icon to the right
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.right), // Replace with your drawable resource
                contentDescription = "Navigate",
                tint = Color.Unspecified,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Profile Option Item Preview")
@Composable
fun PreviewProfileOptionItem() {
    UTS_LECTheme {
        ProfileOptionItem(
            name = "Profile",
            icon = Icons.Default.Person,
            onClick = { /* Do nothing for preview */ }
        )
    }
}