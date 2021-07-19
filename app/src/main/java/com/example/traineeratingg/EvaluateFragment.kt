package com.example.traineeratingg

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.traineeratingg.Data.Firebase.CURRENT_USER
import com.example.traineeratingg.Data.Firebase.NODE_TEAMS
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT

class EvaluateFragment(val taskName: String, val userLogin: String) : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluate, container, false)
    }

    override fun onResume() {
        super.onResume()
        val task = view?.findViewById<TextView>(R.id.task)
        task!!.text = taskName

        val rdGroupFirst = view?.findViewById<RadioGroup>(R.id.group_param1)
        val rdGroupSecond = view?.findViewById<RadioGroup>(R.id.group_param2)
        val rdGroupThird = view?.findViewById<RadioGroup>(R.id.group_param3)
        val rdGroupFourth = view?.findViewById<RadioGroup>(R.id.group_param4)
        val save = view?.findViewById<Button>(R.id.save)
        var m1 = ""
        var m2 = ""
        var m3 = ""
        var m4 = ""
        var M = "$m1$m2$m3$m4"
        rdGroupFirst!!.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.param1mark1 -> {
                    m1 = "-1"
                }
                R.id.param1mark2 -> {
                    m1 = "0"
                }
                R.id.param1mark3 -> {
                    m1 = "1"
                }
                R.id.param1mark4 -> {
                    m1 = "2"
                }
            }
            M = "$m1$m2$m3$m4"
        }

        rdGroupSecond!!.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.param2mark1 -> {
                    m2 = " -1"
                }
                R.id.param2mark2 -> {
                    m2 = " 0"
                }
                R.id.param2mark3 -> {
                    m2 = " 1"
                }
                R.id.param2mark4 -> {
                    m2 = " 2"
                }
            }
            M = "$m1$m2$m3$m4"
        }

        rdGroupThird!!.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.param3mark1 -> {
                    m3 = " -1"
                }
                R.id.param3mark2 -> {
                    m3 = " 0"
                }
                R.id.param3mark3 -> {
                    m3 = " 1"
                }
                R.id.param3mark4 -> {
                    m3 = " 2"
                }
            }
            M = "$m1$m2$m3$m4"
        }

        rdGroupFourth!!.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.param4mark1 -> {
                    m4 = " -1"
                }
                R.id.param4mark2 -> {
                    m4 = " 0"
                }
                R.id.param4mark3 -> {
                    m4 = " 1"
                }
                R.id.param4mark4 -> {
                    m4 = " 2"
                }
            }
            M = "$m1$m2$m3$m4"
            save!!.setOnClickListener {
                REF_DATABASE_ROOT.child(NODE_USERS).child(userLogin).addListenerForSingleValueEvent(
                        AppValueEventListener{
                            val user = it.getValue(User::class.java)
                            REF_DATABASE_ROOT.child(NODE_TEAMS).child(user!!.team).child(NODE_USERS).child(userLogin).child(taskName).child(CURRENT_USER).setValue(M)
                        }
                )
                if (userLogin== CURRENT_USER){
                    replaceFragment(UserAnalyticFragment())
                } else{
                    replaceFragment(TeamFragment())
                }
            }
        }

    }
}