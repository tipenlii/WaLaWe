package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StatisticsScreen() {
    val textBlue = colorResource(id = R.color.text_blue)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textBlue,
            modifier = Modifier.padding(top = 40.dp, bottom = 16.dp)
        )
        OverviewSection()
        CaloriesSection()
        BMISection()
        DailyQuoteSection()
    }
}

@Composable
fun OverviewSection() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatCard(title = "Cal Burnt", value = "3,950", icon = R.drawable.fire, modifier = Modifier.weight(1f))
        StatCard(title = "Total Time", value = "3h 14m", icon = R.drawable.time, modifier = Modifier.weight(1f))
        StatCard(title = "Exercises", value = "15", icon = R.drawable.excercise, modifier = Modifier.weight(1f))
    }
}

@Composable
fun StatCard(title: String, value: String, icon: Int, modifier: Modifier = Modifier) {
    val background_light_blue = colorResource(id = R.color.background_light_blue)
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = background_light_blue),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(), // Ensure the column takes up the full size of the card
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterHorizontally), // Center the icon horizontally
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Center the value horizontally
            )
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Center the title horizontally
            )
        }
    }
}

@Composable
fun CaloriesSection() {
    val buttonBlue = colorResource(id = R.color.button_blue)
    var selectedRange by remember { mutableStateOf("Weekly") }
    val ranges = listOf("Daily", "Weekly", "Yearly")

    Column {
        Text(
            text = "Calories",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp, // Adjust the font size as needed
            modifier = Modifier.padding(start = 2.dp, top = 30.dp) // Adjust the padding from the left
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            ranges.forEach { range ->
                TextButton(
                    onClick = { selectedRange = range },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedRange == range) buttonBlue else Color.Gray
                    )
                ) {
                    Text(text = range, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Placeholder for the graph/bar chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chart), // Replace with your image resource
                contentDescription = "Graph/Bar Chart",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun BMISection() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "BMI", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bmi_chart), // Replace with your image resource
                    contentDescription = "BMI Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DailyQuoteSection() {
    val textBlue = colorResource(id = R.color.text_blue)
    Card(
        modifier = Modifier
            .width(400.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Amazing job finishing today's session! 🎉",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold, // Make the text bold
                color = colorResource(id = R.color.text_blue), // Change the color to buttonBlue
                modifier = Modifier.fillMaxWidth(), // Fill the width of the parent
                textAlign = TextAlign.Center // Center the text horizontally
            )
        }
    }
    // Add Bottom Navigation Bar here
    var currentPage by remember { mutableStateOf("Statistics") }
    val customIcons = listOf(
        painterResource(id = R.drawable.home_icon),
        painterResource(id = R.drawable.statistics_icon),
        painterResource(id = R.drawable.profile_icon)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        BottomNavigationBar(
            currentPage = currentPage,
            onItemSelected = { selectedPage ->
                currentPage = selectedPage
            },
            icons = customIcons
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatisticsScreen() {
    StatisticsScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewOverviewSection() {
    OverviewSection()
}

@Preview(showBackground = true)
@Composable
fun PreviewCaloriesSection() {
    CaloriesSection()
}

@Preview(showBackground = true)
@Composable
fun PreviewBMISection() {
    BMISection()
}

@Preview(showBackground = true)
@Composable
fun PreviewDailyQuoteSection() {
    DailyQuoteSection()
}
