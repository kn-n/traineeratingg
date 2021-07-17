package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.traineeratingg.Data.Firebase.CURRENT_USER
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT
import com.example.traineeratingg.Data.Firebase.initFirebase

class EditAccountFragment: Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_acc, container, false)
    }

    override fun onResume() {
        super.onResume()
        val img = view?.findViewById<LinearLayout>(R.id.img)
        val login = view?.findViewById<EditText>(R.id.login)
        val password = view?.findViewById<EditText>(R.id.password)
        val name = view?.findViewById<EditText>(R.id.name)
        val job = view?.findViewById<EditText>(R.id.job)
        val specialization = view?.findViewById<EditText>(R.id.specialization)
        val skills = view?.findViewById<EditText>(R.id.skills)
        val save = view?.findViewById<Button>(R.id.save)

        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    login!!.setText(user!!.login)
                    password!!.setText(user!!.password)
                    name!!.setText(user!!.name)
                    job!!.setText(user!!.placeOfStudy)
                    specialization!!.setText(user!!.specialty)
                    skills!!.setText(user!!.skills)
                    save!!.setOnClickListener{
                        user.login = login.text.toString()
                        user.password = password.text.toString()
                        user.name = name.text.toString()
                        user.placeOfStudy = job.text.toString()
                        user.specialty = specialization.text.toString()
                        user.skills = skills.text.toString()
                        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).setValue(user)
                        replaceFragment(AccountFragment())
                    }
                }
        )
    }
}