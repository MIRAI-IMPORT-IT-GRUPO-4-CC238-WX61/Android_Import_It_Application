package com.example.android.android_import_it_application.controllers.activities
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.android_import_it_application.R
import androidx.room.Room
import com.example.android.android_import_it_application.database.MyDatabase

class DirectionLoadActivity : AppCompatActivity(){
    private lateinit var textViewDatos: TextView
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyerdirectionshow)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        textViewDatos = findViewById(R.id.textViewdata)

        mostrarDatosGuardados()
    }

    private fun mostrarDatosGuardados() {
        val nombre = sharedPreferences.getString("name", "")
        val apellido = sharedPreferences.getString("lastname", "")
        val telefono = sharedPreferences.getString("phone", "")
        val dni = sharedPreferences.getString("dni", "")
        val district = sharedPreferences.getString("district", "")
        val province = sharedPreferences.getString("province", "")
        val datos = "Nombre: $nombre \n" +
                "Apellido: $apellido \n" +
                "Telefono: $telefono \n" +
                "Dni: $dni \n" +
                "Distrito: $district \n" +
                "Provincia: $province"
        textViewDatos.text = datos
    }
}

