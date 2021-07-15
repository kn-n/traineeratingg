package com.example.traineeratingg

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        val signUpButton = findViewById<TextView>(R.id.sign_up)
        signUpButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}