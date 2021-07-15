package com.example.traineeratingg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.traineeratingg.Data.Firebase.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        val signUpButton = findViewById<TextView>(R.id.sign_up)
        val signInButton = findViewById<Button>(R.id.sign_in)
        val loginEt = findViewById<EditText>(R.id.login)
        val passwordEt = findViewById<EditText>(R.id.password)
        initFirebase()
        val team = Team("Rocket GO", "Приложение для оценки практикантов", "")
        REF_DATABASE_ROOT.child(NODE_TEAMS).child(team.name).setValue(team)
        REF_DATABASE_ROOT.child(NODE_TEAMS).child(team.name)

        signInButton.setOnClickListener {
            val login = loginEt.text.toString()
            val password = passwordEt.text.toString()
            if (loginEt.text.toString().isEmpty() || passwordEt.text.toString().isEmpty()) {
                makeToast(this, "Fill all the fields")
            } else {
                REF_DATABASE_ROOT.child(NODE_USERS).addListenerForSingleValueEvent(
                        AppValueEventListener {
                            if (it.hasChild(login)) {
                                val user = it.child(login).getValue(User::class.java)
                                if (user!!.password == password) {
                                    CURRENT_USER = user.login
                                    startActivity(Intent(this, MainActivity::class.java))
                                } else {
                                    makeToast(this, "Wrong Password")
                                }
                            }
                        }
                )
            }
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}