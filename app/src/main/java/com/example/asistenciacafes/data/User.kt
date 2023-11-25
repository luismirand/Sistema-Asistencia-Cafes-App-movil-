package com.example.asistenciacafes.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val id: String,
    val Nombres: String,
    val Apellidos: String,
    val email: String
)
