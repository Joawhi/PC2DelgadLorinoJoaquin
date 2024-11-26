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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase

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

        database = FirebaseDatabase.getInstance()

        btCrear.setOnClickListener() {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btIngresar.setOnClickListener() {
            val dni = etDniLogin.text.toString()
            val clave = etClaveLogin.text.toString()

            if (dni.isNotEmpty() && clave.isNotEmpty()) {
                verifyLogin(dni, clave)
            } else {
                Toast.makeText(this, "EL USUARIO y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun verifyLogin(dni: String, clave: String) {
        val usersRef = database.reference.child("usuarios")

        usersRef.orderByChild("DNI").equalTo(dni).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if (user != null && user.CLAVE == clave) {
                            Toast.makeText(this@LoginActivity, "ACCESO PERMITIDO", Toast.LENGTH_SHORT).show()
                            finish()
                            return
                        }
                    }
                    Toast.makeText(this@LoginActivity, "EL USUARIO y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "EL USUARIO y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Este método maneja los errores en la consulta de datos
                Toast.makeText(this@LoginActivity, "EL USUARIO y/O CLAVE NO EXISTE EN EL SISTEMA", Toast.LENGTH_SHORT).show()
            }
        })
    }


    data class User(val CLAVE: String = "", val DNI: String = "", val NOMBRE: String = "")

}