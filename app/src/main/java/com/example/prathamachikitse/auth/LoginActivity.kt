package com.example.prathamachikitse.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.R
import com.example.prathamachikitse.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val loginBtn = findViewById<Button>(R.id.loginButton)
        val registerBtn = findViewById<TextView>(R.id.registerButton)
        val forgotPassword = findViewById<TextView>(R.id.forgotPasswordText)
        val googleBtn = findViewById<Button>(R.id.googleSignInButton)

        loginBtn.setOnClickListener {
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()

            // Bypassing login: accepts ANY email and password
            if (mail.isNotEmpty() && pass.isNotEmpty()) {
                Toast.makeText(this, "Login Successful (Bypassed)", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter any email and password to proceed", Toast.LENGTH_SHORT).show()
            }
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset link sent (Simulated)", Toast.LENGTH_SHORT).show()
        }

        googleBtn.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}
