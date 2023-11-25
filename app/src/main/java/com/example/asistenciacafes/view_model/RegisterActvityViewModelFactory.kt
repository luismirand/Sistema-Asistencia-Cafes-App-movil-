package com.example.asistenciacafes.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asistenciacafes.repository.AuthRepository
import java.security.InvalidParameterException

class RegisterActvityViewModelFactory(
    private val authRepository: AuthRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterActivityViewModel::class.java)) {
            return RegisterActivityViewModel(authRepository, application) as T
        }
        throw InvalidParameterException("Unable to construct RegisterActivityViewModel")
    }
}