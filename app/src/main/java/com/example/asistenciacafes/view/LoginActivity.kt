package com.example.asistenciacafes.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.asistenciacafes.R
import com.example.asistenciacafes.databinding.ActivityLoginBinding
import com.example.asistenciacafes.utils.VibrateView
import com.example.asistenciacafes.view_model.LoginActivityViewModel


class LoginActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        mBinding.iniciarSesionBtn.setOnClickListener(this)
        mBinding.registerBtn.setOnClickListener(this)
        mBinding.correoelectronicoEt.onFocusChangeListener = this
        mBinding.contrasenaEt.onFocusChangeListener = this
        mBinding.contrasenaEt.setOnClickListener(this)
        //mViewModel = ViewModelProvider(this, LoginActivityViewModelFactory(AuthRepository(APIService.getService()), application)).get(LoginActivityViewModel::class.java)
        setupObservers()

        setContentView(mBinding.root)
    }

    private fun setupObservers() {
//        mViewModel.getIsLoading().observe(this){
//                mBinding.progressBar.isVisible = it
//        }

        mViewModel.getErrorMessage().observe(this) {
            //nombre, appellido, contraseña
            val formErrorKeys = arrayOf("nombre", "appellido", "email", "password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains(entry.key)) {
                    when (entry.key) {

                        "email" -> {
                            mBinding.correoelectronicoTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }

                        }

                        "password" -> {
                            mBinding.contrasenaTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }

                        }
                    }
                } else {
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("Información")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }
                        .show()
                }
            }

        }
        mViewModel.getUser().observe(this) {
            if (it != null) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }


    private fun validateEmail(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value = mBinding.correoelectronicoEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Correo necesario"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Correo electrónico invalido"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.correoelectronicoTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@LoginActivity, this)
            }
        }
        return errorMessage == null
    }


    private fun validatePassword(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value = mBinding.contrasenaEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Contraseña necesaria"
        } else if (value.length < 6) {
            errorMessage = "Contraseña debe ser de más de 6 caracteres"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.contrasenaTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@LoginActivity, this)
            }
        }
        return errorMessage == null
    }
    private fun validate(): Boolean {
        var isValid = true

        if (!validateEmail(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false

        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)
        return isValid
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when(view.id){
                R.id.iniciarSesionBtn ->{
                    //submitForm()
                    startActivity(Intent(this, HomeActivity::class.java))  //entrar a la HomeActivity directamente
                }
                R.id.registerBtn ->{
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
        }
    }



    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {

                R.id.correoelectronicoEt -> {
                    if (hasFocus) {
                        if (mBinding.correoelectronicoTil.isErrorEnabled) {
                            mBinding.correoelectronicoTil.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }

                R.id.contrasenaEt -> {
                    if (hasFocus) {
                        if (mBinding.contrasenaTil.isErrorEnabled) {
                            mBinding.contrasenaTil.isErrorEnabled = false
                        }
                    } else {
                        validatePassword()
                    }
                }
            }
        }
    }

    private fun submitForm(){
        if(validate()){
            //verify user credentials
            //mViewModel.loginUser(LoginBody(mBinding.correoelectronicoEt.text!!.toString(),mBinding.contrasenaEt.text!!.toString()))
        }
    }
    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent!!.action == KeyEvent.ACTION_UP){
            submitForm()
        }
        return false
    }
}