package com.example.uts_lec

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    currentPage: String,
    onItemSelected: (String) -> Unit,
    icons: List<Painter>,
    navController: NavController // Add NavHostController parameter
) {
    val items = listOf("Home", "Statistics", "Profile")
    val textBlue = colorResource(id = R.color.text_blue)

    BottomAppBar(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Transparent)
            .height(56.dp)
            .width(320.dp)
            .shadow(10.dp, RoundedCornerShape(60.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = item == currentPage
                val iconColor = if (isSelected) Color.White else Color.Gray

                Box(
                    modifier = Modifier
                        .background(if (isSelected) textBlue else Color.Transparent, RoundedCornerShape(25.dp))
                        .clickable {
                            onItemSelected(item)
                            when (item) {
                                "Home" -> navController.navigate("home")
                                "Profile" -> navController.navigate("profile")
                                // Add other navigation actions as needed
                            }
                        }
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