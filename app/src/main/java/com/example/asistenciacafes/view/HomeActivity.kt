package com.example.asistenciacafes.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistenciacafes.R
import com.example.asistenciacafes.databinding.ActivityHomeBinding
import com.google.zxing.integration.android.IntentIntegrator

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.qrBtn.setOnClickListener { initScanner() }
        binding.horarioBtn.setOnClickListener(this)
        binding.historialBtn.setOnClickListener(this)

//        binding.historialBtn.setOnClickListener {
//            val intent = Intent(this, HistorialActivity::class.java)
//            startActivity(intent)
//        }

    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES) //cambiar el tipo de codigos que puede leer
        integrator.setPrompt("Lee el QR")
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "El valor escaneado es: ${result.contents}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)

        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.horarioBtn -> {
                    TODO("implementar horario")
                    //startActivity(Intent(this, HorarioActivity::class.java))
                }
                R.id.historialBtn -> {
                    startActivity(Intent(this, HistorialActivity::class.java))
                }
            }
        }
    }
}