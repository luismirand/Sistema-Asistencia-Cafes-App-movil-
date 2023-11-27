package com.example.asistenciacafes.utils

import com.example.asistenciacafes.data.RegisterBody
import com.example.asistenciacafes.data.RegisterResponse
import com.example.asistenciacafes.data.UniqueEmailValidationResponse
import com.example.asistenciacafes.data.ValidateEmailBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIConsumer {
    @POST("user/validate-unique-email") //TODO "falta poner la ruta"
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @POST("user/register")
    suspend fun registerUser(@Body Body: RegisterBody): Response<RegisterResponse>

}