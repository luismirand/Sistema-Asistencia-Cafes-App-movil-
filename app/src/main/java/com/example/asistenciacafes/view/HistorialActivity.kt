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
import com.example.asistenciacafes.data.Asistencias

class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val listView: ListView = findViewById(R.id.listView)

        // Crea el adaptador y configúralo en el ListView
        val adapter = AsistenciaAdapter(this, R.layout.list_item_layout, Asistencias.asistenciaList)
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