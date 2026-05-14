package com.example.prathamachikitse.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.prathamachikitse.dashboard.DashboardActivity
import com.example.prathamachikitse.utils.Constants

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = EditText(this)
        val password = EditText(this)
        val loginBtn = Button(this)
        val register = TextView(this)
        val forgot = TextView(this)
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)

        email.hint = "Email"
        password.hint = "Password"
        loginBtn.text = "Login"
        register.text = "Create Account"
        forgot.text = "Forgot Password?"

        loginBtn.setOnClickListener {
            if (email.text.toString() == Constants.demoEmail &&
                password.text.toString() == Constants.demoPassword
            ) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgot.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)
        layout.setBackgroundColor(Color.WHITE)
        email.setPadding(20,20,20,20)
        password.setPadding(20,20,20,20)

        layout.addView(email)
        layout.addView(password)
        layout.addView(loginBtn)
        layout.addView(register)
        layout.addView(forgot)

        setContentView(layout)
        loginBtn.setOnClickListener {
            FirebaseAuthHelper.login(
                email.text.toString(),
                password.text.toString()
            ) { success ->
                if (success) {
                    startActivity(Intent(this, DashboardActivity::class.java))
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}