package com.example.lab_1.data.model

data class HouseConfig(
    val target_temperature: Double,
    val living_room_lights: String,
    val hvac_mode: String,
    val security_system: String,
    val last_updated_by: String
)