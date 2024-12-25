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
        "arm_circles_clockwise" -> R.drawable.arm_circles_clockwise
        "diagonal_plank" -> R.drawable.diagonal_plank
        "arm_raises" -> R.drawable.arm_raises
        "leg_barbel_curl" -> R.drawable.leg_barbel_curl
        "pushup" -> R.drawable.pushup
        "side_arm_raise" -> R.drawable.side_arm_raise
        "standing_biceps_stretch" -> R.drawable.standing_biceps_stretch
        "triceps_dips" -> R.drawable.triceps_dips
        "triceps_stretch" -> R.drawable.triceps_stretch
        "wall_pushup" -> R.drawable.wall_pushup
        "wide_arm_pushup" -> R.drawable.wide_arm_pushup
        "chest_stretch" -> R.drawable.chest_stretch
        "diamond_pushup" -> R.drawable.diamond_pushup
        "back_arches" -> R.drawable.back_arches
        "arm_curls_crunch_left" -> R.drawable.arm_curls_crunch_left
        "incline_pushup" -> R.drawable.incline_pushup
        "knee_pushup" -> R.drawable.knee_pushup
        "pike_pushup" -> R.drawable.pike_pushup
        "adductor_stretch_in_standing" -> R.drawable.adductor_stretch_in_standing
        "wall_calf_raises" -> R.drawable.wall_calf_raises
        "backward_lunge" -> R.drawable.backward_lunge
        "donkey_kicks" -> R.drawable.donkey_kicks
        "knee_to_chest_stretch" -> R.drawable.knee_to_chest_stretch
        "quad_stretch_with_wall" -> R.drawable.quad_stretch_with_wall
        "side_hop" -> R.drawable.side_hop
        "sidelying_leg_lift" -> R.drawable.sidelying_leg_lift
        "squats" -> R.drawable.squats
        "sumo_squat_calf_raises_with_wall" -> R.drawable.sumo_squat_calf_raises_with_wall


        else -> R.drawable.pushup
    }
}