package com.example.uts_lec

fun getImageResourceId(imageName: String): Int {
    return when (imageName) {
        "abdominal_crunches" -> R.drawable.abdominal_crunches
        "bicycle_crunches" -> R.drawable.bicycle_crunches
        "cobra_stretch" -> R.drawable.cobra_stretch
        "heel_touch" -> R.drawable.heel_touch
        "jumping_jack" -> R.drawable.jumping_jack
        "leg_in_outs" -> R.drawable.leg_in_outs
        "mountain_climber" -> R.drawable.mountain_climber
        "plank" -> R.drawable.plank
        "russian_twist" -> R.drawable.russian_twist
        "spine_lumbar_twist_stretch" -> R.drawable.spine_lumbar_twist_stretch

        else -> R.drawable.pushup
    }
}