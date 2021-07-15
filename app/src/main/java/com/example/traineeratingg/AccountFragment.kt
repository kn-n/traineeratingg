package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.traineeratingg.Data.Firebase.CURRENT_USER
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT
import com.example.traineeratingg.Data.Firebase.initFirebase

class AccountFragment: Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_acc, container, false)
    }

    override fun onResume() {
        super.onResume()
        val role = view?.findViewById<TextView>(R.id.status)
        val name = view?.findViewById<TextView>(R.id.name)
        val team = view?.findViewById<TextView>(R.id.team)
        val job = view?.findViewById<TextView>(R.id.job)
        val specialization = view?.findViewById<TextView>(R.id.specialization)
        val skills = view?.findViewById<TextView>(R.id.skills)
        val img = view?.findViewById<ImageView>(R.id.img)

        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    role!!.text = user!!.role
                    name!!.text = user!!.name
                    team!!.text = user!!.team
                    job!!.text = user!!.placeOfStudy
                    specialization!!.text = user!!.specialty
                    skills!!.text = user!!.skills
                }
        )
    }
}