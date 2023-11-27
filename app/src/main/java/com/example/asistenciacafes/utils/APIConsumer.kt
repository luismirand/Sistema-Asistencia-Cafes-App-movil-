package com.example.asistenciacafes.utils

import com.example.asistenciacafes.data.RegisterBody
import com.example.asistenciacafes.data.AuthResponse
import com.example.asistenciacafes.data.LoginBody
import com.example.asistenciacafes.data.UniqueEmailValidationResponse
import com.example.asistenciacafes.data.ValidateEmailBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIConsumer {
//    @POST("/api/integrante")
//    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @POST("/api/integrante")
    suspend fun registerUser(@Body Body: RegisterBody): Response<AuthResponse>

    @POST("/token")
    suspend fun loginUser(@Body Body: LoginBody): Response<AuthResponse>

}