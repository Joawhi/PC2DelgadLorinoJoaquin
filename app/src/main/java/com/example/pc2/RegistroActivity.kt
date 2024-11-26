package com.example.pc2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etRegDni: EditText = findViewById(R.id.etRegDni)
        val etClaveLogin: EditText = findViewById(R.id.etRegName)
        val etRegClave: EditText = findViewById(R.id.etRegClave)
        val etRegClaveConf: EditText = findViewById(R.id.etRegClaveConf)
        val btRegistrar: Button = findViewById(R.id.btnRegistrar)

        btRegistrar.setOnClickListener() {
            val dni = etRegDni.text.toString()
            val name = etClaveLogin.text.toString()
            val password = etRegClave.text.toString()
            val passwordConf = etRegClaveConf.text.toString()
            if (password == passwordConf) {
                registerUser(dni, name, password)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }



    }
    private fun registerUser(dni: String, name: String, password: String) {
        val user = User(dni, name, password)
        val userRef = database.reference.child("usuarios").push()
        userRef.setValue(user)
            .addOnCompleteListener { dbTask ->
                if (dbTask.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }

    data class User(val CLAVE: String, val DNI: String, val NOMBRE: String)
}