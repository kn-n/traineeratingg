package com.example.traineeratingg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT
import com.example.traineeratingg.Data.Firebase.initFirebase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        initFirebase()
        val loginEt = findViewById<EditText>(R.id.login)
        val passwordEt = findViewById<EditText>(R.id.password)
        val nameEt = findViewById<EditText>(R.id.name)
        val jobEt = findViewById<EditText>(R.id.job)
        val specializationEt = findViewById<EditText>(R.id.specialization)
        val skillsEt = findViewById<EditText>(R.id.skills)
        val createAccButton = findViewById<Button>(R.id.sign_in)

        createAccButton.setOnClickListener {
            if (loginEt.text.toString().isEmpty() || passwordEt.text.toString().isEmpty() ||
                nameEt.text.toString().isEmpty() || jobEt.text.toString().isEmpty()||
                specializationEt.text.toString().isEmpty() || skillsEt.text.toString().isEmpty()){
                makeToast(this,"Заполните все поля")
            } else{
                val user = User(loginEt.text.toString(),passwordEt.text.toString(),"","","","",
                    nameEt.text.toString(),jobEt.text.toString(),
                    specializationEt.text.toString(),skillsEt.text.toString())
                REF_DATABASE_ROOT.child(NODE_USERS).child(user.login).setValue(user)
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
    }
}