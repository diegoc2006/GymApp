package com.example.gymapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Intent

class MainActivity : AppCompatActivity() {

    lateinit var txtNombre: EditText
    lateinit var spinner: Spinner
    lateinit var radioCardio: RadioButton
    lateinit var radioFuerza: RadioButton
    lateinit var checkActivo: CheckBox
    lateinit var btnGuardar: Button

    lateinit var btnConsultar: Button

    var url = "http://10.0.2.2/GymAPI/insertar.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtNombre = findViewById(R.id.txtNombre)
        spinner = findViewById(R.id.spinner)
        radioCardio = findViewById(R.id.radioCardio)
        radioFuerza = findViewById(R.id.radioFuerza)
        checkActivo = findViewById(R.id.checkActivo)
        btnGuardar = findViewById(R.id.btnGuardar)

        val dificultad = arrayOf("Facil", "Media", "Dificil")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            dificultad
        )

        spinner.adapter = adapter

        btnGuardar.setOnClickListener {
            insertar()
        }

        btnConsultar = findViewById(R.id.btnConsultar)

        btnConsultar.setOnClickListener {

            val intent = Intent(this, ConsultarActivity::class.java)
            startActivity(intent)

        }
    }

    private fun insertar() {

        val nombre = txtNombre.text.toString()
        val dificultad = spinner.selectedItem.toString()

        var tipo = ""

        if (radioCardio.isChecked) tipo = "Cardio"
        if (radioFuerza.isChecked) tipo = "Fuerza"

        val activo = if (checkActivo.isChecked) "Si" else "No"

        val queue = Volley.newRequestQueue(this)

        val request = object : StringRequest(
            Request.Method.POST, url,
            { response ->

                Toast.makeText(this, response, Toast.LENGTH_LONG).show()

            },
            { error ->

                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()

            }
        ) {

            override fun getParams(): MutableMap<String, String> {

                val params = HashMap<String, String>()

                params["nombre"] = nombre
                params["tipo"] = tipo
                params["dificultad"] = dificultad
                params["activo"] = activo

                return params
            }
        }

        queue.add(request)
    }
}