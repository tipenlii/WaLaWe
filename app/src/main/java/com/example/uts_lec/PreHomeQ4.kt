package com.example.uts_lec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

@Composable
fun IntroductionScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.prehomeq4),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContinueButton(onClick = {
                if (userId != null) {
                    db.collection("users").document(userId).get().addOnSuccessListener { document ->
                        val gender = document.getString("gender") ?: "Unknown"
                        val age = document.getLong("age")?.toInt() ?: 0
                        val height = document.getLong("height")?.toInt() ?: 0
                        val weight = document.getLong("weight")?.toInt() ?: 0
                        val goal = document.getString("goal") ?: "Unknown"
                        val difficulty = document.getString("difficulty") ?: "Unknown"

                        val classification = classifyWorkout(
                            gender = gender,
                            age = age,
                            height = height,
                            weight = weight,
                            goal = goal,
                            difficulty = difficulty
                        )

                        val userUpdates = hashMapOf(
                            "classification" to classification
                        )
                        db.collection("users").document(userId)
                            .set(userUpdates, SetOptions.merge())
                            .addOnSuccessListener {
                                navController.navigate("home")
                            }
                    }
                }
            })
        }
    }
}

fun classifyWorkout(
    gender: String,
    age: Int,
    height: Int,
    weight: Int,
    goal: String,
    difficulty: String
): String {
    // Menentukan ageRange
    val ageRange = when {
        age <= 17 -> "Under17"
        age in 18..40 -> "18to40"
        else -> "Above40"
    }

    // Menentukan heightRange
    val heightRange = when {
        height < 140 -> "Under140cm"
        height in 140..170 -> "140to170cm"
        else -> "Above170cm"
    }

    // Menentukan hasil berdasarkan ageRange, heightRange, goal, dan difficulty
    return when (ageRange) {
        "Under17" -> when (heightRange) {
            "Under140cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            "140to170cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            "Above170cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            else -> "InvalidHeightRange"
        }
        "18to40" -> when (heightRange) {
            "Under140cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            "140to170cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            "Above170cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            else -> "InvalidHeightRange"
        }
        "Above40" -> when (heightRange) {
            "Under140cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            "140to170cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            "Above170cm" -> when (goal) {
                "WeightLoss" -> when (difficulty) {
                    "Beginner" -> "WeightLossBeginner"
                    "Intermediate" -> "WeightLossIntermediate"
                    "Advanced" -> "WeightLossAdvanced"
                    else -> "InvalidDifficulty"
                }
                "MuscleBuilding" -> when (difficulty) {
                    "Beginner" -> "MuscleBuildingBeginner"
                    "Intermediate" -> "MuscleBuildingIntermediate"
                    "Advanced" -> "MuscleBuildingAdvanced"
                    else -> "InvalidDifficulty"
                }
                "Endurance" -> when (difficulty) {
                    "Beginner" -> "EnduranceBeginner"
                    "Intermediate" -> "EnduranceIntermediate"
                    "Advanced" -> "EnduranceAdvanced"
                    else -> "InvalidDifficulty"
                }
                else -> "InvalidGoal"
            }
            else -> "InvalidHeightRange"
        }
        else -> "InvalidAgeRange"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntroductionScreen() {
    IntroductionScreen(navController = rememberNavController())
}