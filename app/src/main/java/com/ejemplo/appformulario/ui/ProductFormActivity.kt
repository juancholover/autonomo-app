package com.ejemplo.appformulario.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.ejemplo.appformulario.R
import com.ejemplo.appformulario.databinding.ActivityProductFormBinding
import com.google.android.material.textfield.TextInputLayout

class ProductFormActivity : ComponentActivity() {

    private lateinit var b: ActivityProductFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProductFormBinding.inflate(layoutInflater)
        setContentView(b.root)
        title = getString(R.string.product_form_title)

        // Spinner categorías desde resources
        ArrayAdapter.createFromResource(
            this,
            R.array.product_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            b.spCategory.adapter = adapter
        }

        b.btnSaveProduct.setOnClickListener {
            if (validate()) {
                val name = b.etProductName.text?.toString()?.trim().orEmpty()
                val price = b.etPrice.text?.toString()?.trim()?.toDoubleOrNull() ?: 0.0
                val category = b.spCategory.selectedItem?.toString().orEmpty()
                val available = b.cbAvailable.isChecked
                // En un caso real: guardar en DB/Room o enviar a servidor.
                Toast.makeText(this, getString(R.string.msg_saved), Toast.LENGTH_SHORT).show()
                // Limpieza simple
                b.etProductName.setText(""); b.etPrice.setText("")
                b.spCategory.setSelection(0); b.cbAvailable.isChecked = false
            }
        }
    }

    private fun validate(): Boolean {
        var ok = true
        ok = b.tilProductName.required(b.etProductName.text) && ok
        ok = b.tilPrice.required(b.etPrice.text) && ok

        val price = b.etPrice.text?.toString()?.trim()?.toDoubleOrNull()
        if (price == null || price < 0.0) {
            b.tilPrice.error = getString(R.string.msg_price_invalid)
            ok = false
        } else {
            b.tilPrice.error = null
        }
        return ok
    }

    private fun TextInputLayout.required(text: CharSequence?): Boolean {
        return if (text.isNullOrBlank()) {
            error = getString(R.string.msg_required); false
        } else { error = null; true }
    }
}