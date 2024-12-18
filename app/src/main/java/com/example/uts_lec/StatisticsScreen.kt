package com.example.uts_lec

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.Brush

@Composable
fun StatisticsScreen(navController: NavController) {
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
        OverviewSection1()
        CaloriesSection()
        BMISection()
        DailyQuoteSection(navController)
    }
}

@Composable
fun OverviewSection1() {
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun BarChart(
    data: List<Float>,
    labels: List<String>,
    maxValue: Float,
    barPadding: Float = 16f
) {
    var selectedBarIndex by remember { mutableStateOf(-1) }
    val baseColor = Color(0xFFE6F1FF)
    val progressColor = Color(0xFF80BBFF)
    var selectedBarX by remember { mutableStateOf(0.dp) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val barWidth = (size.width - (data.size + 1) * barPadding) / data.size
                    val tappedIndex = (tapOffset.x / (barWidth + barPadding)).toInt()
                    if (tappedIndex in data.indices) {
                        selectedBarIndex = tappedIndex
                        selectedBarX = (tappedIndex * (barWidth + barPadding) + barWidth / 2).dp
                    }
                }
            }
        ) {
            val barWidth = (size.width - (data.size + 1) * barPadding) / data.size
            val maxBarHeight = size.height

            data.forEachIndexed { index, value ->
                val barHeight = (value / maxValue) * maxBarHeight
                val x = barPadding + index * (barWidth + barPadding)
                val y = size.height - barHeight

                // Draw base color
                drawRoundRect(
                    color = baseColor,
                    topLeft = Offset(x, 0f),
                    size = androidx.compose.ui.geometry.Size(barWidth, size.height),
                    cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                )

                // Draw progress color
                drawRoundRect(
                    color = progressColor,
                    topLeft = Offset(x, y),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                )

                // Draw label text
                drawIntoCanvas { canvas ->
                    val textPaint = android.graphics.Paint().apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 34f
                        color = android.graphics.Color.DKGRAY
                        typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD) // Bold text
                    }
                    canvas.nativeCanvas.drawText(
                        labels[index],
                        x + barWidth / 2,
                        size.height + 20f + 8.dp.toPx(),
                        textPaint
                    )
                }
            }
        }
    }
}


@Composable
fun CaloriesSection() {
    val buttonBlue = colorResource(id = R.color.button_blue)
    var selectedRange by remember { mutableStateOf("Weekly") }
    val ranges = listOf("Daily", "Weekly", "Yearly")
    var expanded by remember { mutableStateOf(false) }

    // Define calorie data for each range
    val (calorieData, labels, maxValue) = when (selectedRange) {
        "Daily" -> Triple(
            listOf(150f, 220f, 180f, 90f, 130f, 200f, 250f),
            listOf("1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM"),
            300f
        )
        "Weekly" -> Triple(
            listOf(120f, 300f, 540f, 150f, 220f, 180f, 320f),
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
            600f
        )
        "Yearly" -> Triple(
            listOf(2000f, 2500f, 2700f, 2300f, 2400f, 2600f, 2800f),
            listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"),
            3000f
        )
        else -> Triple(emptyList(), emptyList(), 0f)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Calories",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Box {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEBF4FF))
                ) {
                    Text(text = selectedRange, color = Color(0xFF0070F0))
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    ranges.forEach { range ->
                        Button(
                            onClick = {
                                selectedRange = range
                                expanded = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(text = range)
                        }
                    }
                }
            }
        }

        // Chart Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            BarChart(data = calorieData, labels = labels, maxValue = maxValue, barPadding = 90f)
        }
    }
}


@Composable
fun BMISection() {
    val bmiData = listOf(18.5f, 20f, 22f, 24f, 25.5f, 23.8f, 21f) // Example BMI values over time
    val labels = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")
    val maxValue = 30f // Maximum BMI to scale the chart

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "BMI Trends",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 2.dp, top = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            LineChart(data = bmiData, labels = labels, maxValue = maxValue)
        }
    }
}

@Composable
fun LineChart(data: List<Float>, labels: List<String>, maxValue: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val padding = 50f
        val chartWidth = size.width - padding * 2
        val chartHeight = size.height - padding * 2
        val xInterval = chartWidth / (data.size - 1)
        val yScale = chartHeight / maxValue

        // Draw the line
        for (i in 0 until data.size - 1) {
            val x1 = padding + i * xInterval
            val y1 = size.height - padding - data[i] * yScale
            val x2 = padding + (i + 1) * xInterval
            val y2 = size.height - padding - data[i + 1] * yScale

            drawLine(
                color = Color(0xFF80BBFF),
                start = androidx.compose.ui.geometry.Offset(x1, y1),
                end = androidx.compose.ui.geometry.Offset(x2, y2),
                strokeWidth = 4f
            )
        }

        // Draw points and labels
        data.forEachIndexed { index, value ->
            val x = padding + index * xInterval
            val y = size.height - padding - value * yScale

            drawCircle(
                color = Color(0xFF11579D),
                center = androidx.compose.ui.geometry.Offset(x, y),
                radius = 8f
            )

            drawIntoCanvas { canvas ->
                translate(left = x, top = size.height - padding / 2) {
                    canvas.nativeCanvas.drawText(
                        labels[index],
                        0f,
                        0f,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 30f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DailyQuoteSection(navController: NavController) {
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
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.text_blue),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

    var currentPage by remember { mutableStateOf("statistics") }
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
            currentPage = "statistics",
            onItemSelected = { page ->
                when (page) {
                    "home" -> navController.navigate("home")
                    "profile" -> navController.navigate("profile")
                    "statistics" -> navController.navigate("statistics")
                }
            },
            navController = navController,
            icons = customIcons
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatisticsScreen() {
    val navController = rememberNavController()
    StatisticsScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun PreviewOverviewSection() {
    OverviewSection1()
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
    val navController = rememberNavController()
    DailyQuoteSection(navController = navController)
}