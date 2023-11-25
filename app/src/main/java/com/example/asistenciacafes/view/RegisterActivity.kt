package com.example.asistenciacafes.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.asistenciacafes.R
import com.example.asistenciacafes.data.RegisterBody
import com.example.asistenciacafes.data.ValidateEmailBody
import com.example.asistenciacafes.databinding.ActivityRegisterBinding
import com.example.asistenciacafes.repository.AuthRepository
import com.example.asistenciacafes.utils.APIService
import com.example.asistenciacafes.view_model.RegisterActivityViewModel
import com.example.asistenciacafes.view_model.RegisterActvityViewModelFactory

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener, TextWatcher {
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.nombresEt.onFocusChangeListener = this
        mBinding.appellidosEt.onFocusChangeListener = this
        mBinding.correoelectronicoEt.onFocusChangeListener = this
        mBinding.contrasenaEt.onFocusChangeListener = this
        mBinding.concontrasenaEt.setOnKeyListener(this)
        mBinding.concontrasenaEt.onFocusChangeListener = this
        mBinding.concontrasenaEt.addTextChangedListener(this)
        mBinding.registrarBtn.setOnClickListener(this)
        //mViewModel = ViewModelProvider(this, RegisterActvityViewModelFactory(AuthRepository(APIService.getService()), application)).get(RegisterActivityViewModel::class.java)
        //setupObservers()
    }
    private fun setupObservers(){
//        mViewModel.getIsLoading().observe(this){
//                mBinding.progressBar.isVisible = it
//        }

        mViewModel.getIsUniqueEmail().observe(this){
            if(validateEmail(shouldUpdateView = false)){
                if(it){
                    mBinding.correoelectronicoTil.apply {
                        if(isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.baseline_check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                }else{
                    mBinding.correoelectronicoTil.apply {
                        if(startIconDrawable != null) startIconDrawable=null
                        isErrorEnabled=true
                        error = "Correo ya registrado"
                    }
                }
            }
        }
        mViewModel.getErrorMessage().observe(this){
            //nombre, appellido, contraseña
            val formErrorKeys = arrayOf("nombre", "appellido", "email", "password")
            val message = StringBuilder()
            it.map { entry ->
                if(formErrorKeys.contains(entry.key)){
                    when(entry.key){
                        "nombre" -> {
                            mBinding.nombresTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "apellido"->{
                            mBinding.appellidosTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }

                        }
                        "email" ->{
                            mBinding.correoelectronicoTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }

                        }
                        "password"->{
                            mBinding.contrasenaTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }

                        }
                    }
                } else{
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()){
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("Información")
                        .setMessage(message)
                        .setPositiveButton("OK"){ dialog, _ -> dialog!!.dismiss()}
                        .show()
                }
            }

        }
        mViewModel.getUser().observe(this){
            if (it != null){
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
    private fun validateName(): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.nombresEt.text.toString()

        if (value.isEmpty()) {
            errorMessage = "Nombre (s) necesarios"
        }

        if (errorMessage != null) {
            mBinding.nombresTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateApellidos(): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.appellidosEt.text.toString()

        if (value.isEmpty()) {
            errorMessage = "Apellidos necesarios"
        }

        if (errorMessage != null) {
            mBinding.appellidosTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateEmail(shouldUpdateView: Boolean = true): Boolean {
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
            }
        }
        return errorMessage == null
    }

    private fun validatePassword(shouldUpdateView: Boolean = true): Boolean {
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
            }
        }
        return errorMessage == null
    }

    private fun validateConfirmPassword(shouldUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.concontrasenaEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Es necesario confirmar Contraseña"
        } else if (value.length < 6) {
            errorMessage = "Contraseña debe ser de más de 6 caracteres"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.concontrasenaTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(shouldUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val password = mBinding.contrasenaEt.text.toString()
        val conpassword = mBinding.concontrasenaEt.text.toString()
        if (password != conpassword) {
            errorMessage = "Contraseñas diferentes"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.concontrasenaTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.registrarBtn){
            onSubmit()
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.nombresEt -> {
                    if (hasFocus) {
                        if (mBinding.nombresTil.isErrorEnabled) {
                            mBinding.nombresTil.isErrorEnabled = false
                        }
                    } else {
                        validateName()
                    }
                }

                R.id.appellidosEt -> {
                    if (hasFocus) {
                        if (mBinding.appellidosTil.isErrorEnabled) {
                            mBinding.appellidosTil.isErrorEnabled = false
                        }
                    } else {
                        validateApellidos()
                    }
                }

                R.id.correoelectronicoEt -> {
                    if (hasFocus) {
                        if (mBinding.correoelectronicoTil.isErrorEnabled) {
                            mBinding.correoelectronicoTil.isErrorEnabled = false
                        }
                    } else {
//                        if (validateEmail()) {
//                            mViewModel.validateEmailAddress(ValidateEmailBody(mBinding.correoelectronicoEt.text!!.toString()))
//
//                        }
                    }

                }

                R.id.contrasenaEt -> {
                    if (hasFocus) {
                        if (mBinding.contrasenaTil.isErrorEnabled) {
                            mBinding.contrasenaTil.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && mBinding.concontrasenaEt.text!!.isNotEmpty() && validateConfirmPassword() &&
                            validatePasswordAndConfirmPassword()
                        ) {
                            if (mBinding.concontrasenaTil.isErrorEnabled) {
                                mBinding.concontrasenaTil.isErrorEnabled = false
                            }
                            mBinding.concontrasenaTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }

                }

                R.id.concontrasenaEt -> {
                    if (hasFocus) {
                        if (mBinding.concontrasenaTil.isErrorEnabled) {
                            mBinding.concontrasenaTil.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (mBinding.contrasenaTil.isErrorEnabled) {
                                mBinding.contrasenaTil.isErrorEnabled = false
                            }
                            mBinding.concontrasenaTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if(KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP){
            onSubmit()
        }
        return false
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(validatePassword(shouldUpdateView = false) && validateConfirmPassword(shouldUpdateView = false) && validatePasswordAndConfirmPassword(shouldUpdateView = false)){
            mBinding.concontrasenaTil.apply {
                if(isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        }else{
            if(mBinding.concontrasenaTil.startIconDrawable != null)
                mBinding.concontrasenaTil.startIconDrawable = null
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    private fun onSubmit(){
        if (validate()){
            //TODO make api request
            mViewModel.registerUser(RegisterBody(mBinding.nombresEt.text!!.toString(), mBinding.appellidosEt.text!!.toString(),
                mBinding.correoelectronicoEt.text!!.toString(), mBinding.contrasenaEt.text!!.toString()))

        }
    }
    private fun validate(): Boolean{
        var isValid = true

        if (!validateName()) isValid = false
        if (!validateApellidos()) isValid = false
        if (!validateEmail()) isValid =  false
        if (!validatePassword()) isValid = false
        if (!validateConfirmPassword()) isValid = false
        if (isValid && validatePasswordAndConfirmPassword()) isValid = false

        return isValid
    }
}

