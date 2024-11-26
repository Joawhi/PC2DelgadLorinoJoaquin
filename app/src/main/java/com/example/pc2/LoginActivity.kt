package com.example.pc2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etDniLogin: EditText = findViewById(R.id.etDni)
        val etClaveLogin: EditText = findViewById(R.id.etClave)
        val btIngresar: Button = findViewById(R.id.btnIngresar)
        val btCrear: Button = findViewById(R.id.btnCrear)

        btCrear.setOnClickListener() {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btIngresar.setOnClickListener() {
            startActivity(Intent(this, RegistroActivity::class.java))
        }



    }





}