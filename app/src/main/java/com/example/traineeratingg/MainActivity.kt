package com.example.traineeratingg

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.traineeratingg.Data.Firebase.CURRENT_USER
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT
import com.example.traineeratingg.Data.Firebase.initFirebase

private val teamFragment = TeamFragment()
private val userAnalyticFragment = UserAnalyticFragment()
private val accountFragment = AccountFragment()

class MainActivity : AppCompatActivity() {
    private val nameOfFragment = findViewById<TextView>(R.id.name_of_activity)
    private val navAnalytics = findViewById<ImageButton>(R.id.analytics)
    private val navTeams = findViewById<ImageButton>(R.id.teams)
    private val navAcc = findViewById<ImageButton>(R.id.acc)
    private val back = findViewById<ImageButton>(R.id.back)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navAnalytics.isSelected = false
        navTeams.isSelected  = true
        navAcc.isSelected = false

        back.visibility=View.GONE

        initFirebase()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    nameOfFragment.text = user!!.team
                }
        )

        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(R.id.frame, teamFragment)
        fragmentManager.commit()
    }

    override fun onResume() {
        super.onResume()

        navAnalytics.isSelected = false
        navTeams.isSelected  = true
        navAcc.isSelected = false

        navAnalytics.setOnClickListener {
            nameOfFragment.text = "Анализ"
            commitFragment(navAnalytics)
        }

        navAcc.setOnClickListener {
            nameOfFragment.text = "Аккаунт"
            commitFragment(navAcc)
        }

        navTeams.setOnClickListener {
            initFirebase()
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                    AppValueEventListener{
                        val user = it.getValue(User::class.java)
                        nameOfFragment.text = user!!.team
                    }
            )
            commitFragment(navTeams)
        }

    }

    fun commitFragment(view: View){
        val fragmentManager = supportFragmentManager.beginTransaction()
        if (view == navAcc){
            navAnalytics.isSelected = false
            navTeams.isSelected  = false
            navAcc.isSelected = true

            fragmentManager.replace(R.id.frame, accountFragment)
            fragmentManager.commit()
        }else if (view == navTeams){
            navAnalytics.isSelected = false
            navTeams.isSelected  = true
            navAcc.isSelected = false

            fragmentManager.replace(R.id.frame, teamFragment)
            fragmentManager.commit()
        }else if(view == navAnalytics){
            navAnalytics.isSelected = true
            navTeams.isSelected  = false
            navAcc.isSelected = false

            fragmentManager.replace(R.id.frame, userAnalyticFragment)
            fragmentManager.commit()
        }
    }
}