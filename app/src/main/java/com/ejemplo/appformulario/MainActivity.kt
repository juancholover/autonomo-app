package com.ejemplo.appformulario


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.ejemplo.appformulario.databinding.ActivityMainBinding
import com.ejemplo.appformulario.ui.ProductFormActivity   // 👈 importa la Activity del subpaquete ui
import com.ejemplo.appformulario.ui.UserFormActivity      // 👈 importa la Activity del subpaquete ui
class MainActivity : ComponentActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnUserForm.setOnClickListener {
            startActivity(Intent(this, UserFormActivity::class.java))
        }
        b.btnProductForm.setOnClickListener {
            startActivity(Intent(this,  ProductFormActivity::class.java))
        }
    }
}