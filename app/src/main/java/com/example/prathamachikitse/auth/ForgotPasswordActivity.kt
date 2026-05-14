package com.example.prathamachikitse.auth

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = EditText(this)
        val btn = Button(this)

        email.hint = "Enter Email"
        btn.text = "Reset Password"

        btn.setOnClickListener {
            Toast.makeText(this, "Reset Link Sent (Demo)", Toast.LENGTH_SHORT).show()
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        layout.addView(email)
        layout.addView(btn)

        setContentView(layout)
    }
}