package com.example.uts_lec

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    currentPage: String, // To determine the active page
    onItemSelected: (String) -> Unit, // Callback for item selection
    icons: List<Painter>, // List of custom icons
) {
    val items = listOf("Home", "Statistics", "Profile")
    val textBlue = colorResource(id = R.color.text_blue)

    BottomAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp)) // Rounded corners for the navbar
            .background(Color.Transparent) // Transparent background
            .height(56.dp) // Adjust the height as needed
            .width(320.dp)
            .shadow(10.dp, RoundedCornerShape(60.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = item == currentPage
                val iconColor = if (isSelected) Color.White else Color.Gray // Change icon color based on selection

                Box(
                    modifier = Modifier
                        .background(if (isSelected) textBlue else Color.Transparent, RoundedCornerShape(25.dp)) // Rounded corners for active item
                        .clickable { onItemSelected(item) } // Handle item click
                        .padding(5.dp)
                        .width(82.dp)
                        .height(30.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = icons[index],
                            contentDescription = item,
                            tint = iconColor,
                            modifier = Modifier.size(25.dp)
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item,
                                color = iconColor,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    var currentPage by remember { mutableStateOf("Home") } // Example current page
    val customIcons = listOf(
        painterResource(id = R.drawable.home_icon), // Replace with your custom drawable resource
        painterResource(id = R.drawable.statistics_icon), // Replace with your custom drawable resource
        painterResource(id = R.drawable.profile_icon) // Replace with your custom drawable resource
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        BottomNavigationBar(
            currentPage = currentPage,
            onItemSelected = { selectedPage ->
                currentPage = selectedPage // Handle navigation item selection here
            },
            icons = customIcons,
        )
    }
}