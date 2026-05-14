package com.example.prathamachikitse.auth

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = EditText(this)
        val password = EditText(this)
        val btn = Button(this)

        email.hint = "New Email"
        password.hint = "New Password"
        btn.text = "Register"

        btn.setOnClickListener {
            Toast.makeText(this, "Account Created (Demo)", Toast.LENGTH_SHORT).show()
            finish()
        }

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        layout.addView(email)
        layout.addView(password)
        layout.addView(btn)

        setContentView(layout)
    }
}