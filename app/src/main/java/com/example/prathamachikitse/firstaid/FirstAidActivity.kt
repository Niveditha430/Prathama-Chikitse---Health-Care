package com.example.prathamachikitse.firstaid

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FirstAidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tv = TextView(this)

        tv.text = """
🚑 FIRST AID GUIDE

🔥 Burns
✅ Do: Cool with water
❌ Don't: Apply oil

🐍 Snake Bite
✅ Do: Keep still
❌ Don't: Cut wound

💔 Heart Attack
✅ Do: Call emergency
❌ Don't: Ignore pain

Kannada:
ಶಾಂತವಾಗಿರಿ ಮತ್ತು ತಕ್ಷಣ ಚಿಕಿತ್ಸೆ ಪಡೆಯಿರಿ
"""

        setContentView(tv)
    }
}