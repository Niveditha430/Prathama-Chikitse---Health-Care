package com.example.prathamachikitse.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.R
import com.example.prathamachikitse.dashboard.DashboardActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val name = findViewById<EditText>(R.id.nameEditText)
        val email = findViewById<EditText>(R.id.registerEmail)
        val password = findViewById<EditText>(R.id.registerPassword)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val loginBackBtn = findViewById<TextView>(R.id.loginBackBtn)

        registerBtn.setOnClickListener {
            val nameStr = name.text.toString().trim()
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()

            if (validateInputs(nameStr, mail, pass)) {
                auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Welcome, $nameStr! Account Created.", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finishAffinity()
                    }
                    .addOnFailureListener { e ->
                        val errorMsg = if (e.message?.contains("CONFIGURATION_NOT_FOUND") == true) {
                            "Auth Error: Please enable Email/Password in Firebase Console."
                        } else {
                            "Registration Failed: ${e.localizedMessage}"
                        }
                        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                    }
            }
        }

        loginBackBtn.setOnClickListener { finish() }
    }

    private fun validateInputs(name: String, emailStr: String, passStr: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your full name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (emailStr.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        
        // Complex Password Pattern: 8+ chars, Uppercase, Lowercase, Number, Special Character
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$".toRegex()
        if (!passwordPattern.matches(passStr)) {
            Toast.makeText(this, "Password must be 8+ chars with Uppercase, Lowercase, Number, and Special character (@#$%^&+=!)", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
