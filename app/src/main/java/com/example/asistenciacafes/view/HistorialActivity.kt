package com.example.asistenciacafes.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.asistenciacafes.R
import kotlin.random.Random

class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val listView: ListView = findViewById(R.id.listView)

        // Datos de prueba
        val asistenciaList = mutableListOf<AsistenciaModel>(
                AsistenciaModel("2023-10-27", "Asistió"),
                AsistenciaModel("2023-10-28", "No asistió"),
                AsistenciaModel("2023-10-29", "Asistió"),
                AsistenciaModel("2023-10-30", "Asistió"),
                AsistenciaModel("2023-10-31", "Asistió"),
                AsistenciaModel("2023-11-01", "No Asistió"),
                AsistenciaModel("2023-11-02", "Asistió"),
                AsistenciaModel("2023-11-03", "Asistió"),
                AsistenciaModel("2023-11-04", "Asistió"),
                AsistenciaModel("2023-11-05", "Asistió"),
                AsistenciaModel("2023-11-06", "Asistió"),
                AsistenciaModel("2023-11-07", "Asistió"),
                AsistenciaModel("2023-11-08", "Asistió"),
                AsistenciaModel("2023-11-09", "Asistió"),
                AsistenciaModel("2023-11-10", "No Asistió"),
                AsistenciaModel("2023-11-11", "Asistió"),
                AsistenciaModel("2023-11-12", "Asistió"),
                AsistenciaModel("2023-11-13", "Asistió"),
        )



        // Crea el adaptador y configúralo en el ListView
        val adapter = AsistenciaAdapter(this, R.layout.list_item_layout, asistenciaList)
        listView.adapter = adapter
    }
}

data class AsistenciaModel(val fecha: String, val asistencia: String)

class AsistenciaAdapter(context: Context, resource: Int, objects: List<AsistenciaModel>) :
    ArrayAdapter<AsistenciaModel>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.list_item_layout, parent, false)

        val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        val asistenciaTextView: TextView = itemView.findViewById(R.id.asistenciaTextView)

        val currentItem = getItem(position)
        fechaTextView.text = currentItem?.fecha
        asistenciaTextView.text = currentItem?.asistencia

        return itemView
    }
}