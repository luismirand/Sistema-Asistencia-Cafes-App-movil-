package com.example.asistenciacafes.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val id: String,
    val nombres: String,
    val apellidos: String,
    val email: String
)
