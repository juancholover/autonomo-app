package com.ejemplo.appformulario.ui
import android.os.Bundle
import android.util.Patterns
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.ejemplo.appformulario.R
import com.ejemplo.appformulario.databinding.ActivityUserFormBinding
import com.google.android.material.textfield.TextInputLayout

class UserFormActivity : ComponentActivity() {

    private lateinit var b: ActivityUserFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUserFormBinding.inflate(layoutInflater)
        setContentView(b.root)
        title = getString(R.string.user_form_title)

        b.btnSaveUser.setOnClickListener {
            if (validate()) {
                val selectedId = b.rgGender.checkedRadioButtonId
                val gender = if (selectedId != -1) findViewById<RadioButton>(selectedId).text else "N/D"
                val name = b.etName.text?.toString()?.trim().orEmpty()
                val email = b.etEmail.text?.toString()?.trim().orEmpty()
                // En un caso real: guardar en DB/Room o enviar a servidor.
                Toast.makeText(this, getString(R.string.msg_saved), Toast.LENGTH_SHORT).show()
                // Limpieza simple
                b.etName.setText(""); b.etEmail.setText(""); b.etPassword.setText("")
                b.rgGender.clearCheck(); b.cbTerms.isChecked = false
            }
        }
    }

    private fun validate(): Boolean {
        var ok = true
        ok = b.tilName.required(b.etName.text) && ok
        ok = b.tilEmail.required(b.etEmail.text) && ok
        ok = b.tilPassword.required(b.etPassword.text) && ok

        val email = b.etEmail.text?.toString()?.trim().orEmpty()
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            b.tilEmail.error = getString(R.string.msg_email_invalid)
            ok = false
        }

        if (!b.cbTerms.isChecked) {
            b.tilPassword.clearError()
            Toast.makeText(this, getString(R.string.msg_terms_required), Toast.LENGTH_SHORT).show()
            ok = false
        }
        return ok
    }

    private fun TextInputLayout.required(text: CharSequence?): Boolean {
        return if (text.isNullOrBlank()) {
            error = getString(R.string.msg_required); false
        } else { error = null; true }
    }

    private fun TextInputLayout.clearError() { error = null }
}