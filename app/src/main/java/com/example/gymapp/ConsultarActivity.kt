package com.example.gymapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class ConsultarActivity : AppCompatActivity() {

    lateinit var lista: ListView
    val url = "http://10.0.2.2/GymAPI/consultar.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar)

        lista = findViewById(R.id.listaEjercicios)

        cargarDatos()
    }

    private fun cargarDatos(){

        val queue = Volley.newRequestQueue(this)

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->

                val datos = ArrayList<String>()

                for(i in 0 until response.length()){

                    val ejercicio = response.getJSONObject(i)

                    val texto = ejercicio.getString("nombre") +
                            " - " +
                            ejercicio.getString("tipo") +
                            " - " +
                            ejercicio.getString("dificultad")

                    datos.add(texto)
                }

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    datos
                )

                lista.adapter = adapter

            },
            { error ->
                error.printStackTrace()
            }
        )

        queue.add(request)
    }
}