package com.example.asistenciacafes.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asistenciacafes.data.LoginBody
import com.example.asistenciacafes.data.RegisterBody
import com.example.asistenciacafes.data.User
import com.example.asistenciacafes.data.ValidateEmailBody
import com.example.asistenciacafes.repository.AuthRepository
import com.example.asistenciacafes.utils.AuthToken
import com.example.asistenciacafes.utils.RequestStatus
import kotlinx.coroutines.launch

class LoginActivityViewModel(val authRepository: AuthRepository, val application: Application) :
    ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var user: MutableLiveData<User> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getUser(): LiveData<User> = user


    fun loginUser(body: LoginBody) {
        viewModelScope.launch {
            authRepository.loginUser(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        user.value = it.data.user
                        //save token using shared preferences
                        AuthToken.getInstance(application.baseContext).token = it.data.token

                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message
                    }
                }
            }
        }
    }
}