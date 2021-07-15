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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nameOfFragment = findViewById<TextView>(R.id.name_of_activity)
        val navAnalytics = findViewById<ImageButton>(R.id.analytics)
        val navTeams = findViewById<ImageButton>(R.id.teams)
        val navAcc = findViewById<ImageButton>(R.id.acc)
        val back = findViewById<ImageButton>(R.id.back)

        navAnalytics.isSelected = false
        navTeams.isSelected  = true
        navAcc.isSelected = false

        back.visibility=View.GONE
        nameOfFragment.text = "Команда"

        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(R.id.frame, teamFragment)
        fragmentManager.commit()
    }

    override fun onResume() {
        super.onResume()
        val nameOfFragment = findViewById<TextView>(R.id.name_of_activity)
        val navAnalytics = findViewById<ImageButton>(R.id.analytics)
        val navTeams = findViewById<ImageButton>(R.id.teams)
        val navAcc = findViewById<ImageButton>(R.id.acc)

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
            nameOfFragment.text = "Команда"
            commitFragment(navTeams)
        }

    }

    private fun commitFragment(view: View){
        val navAnalytics = findViewById<ImageButton>(R.id.analytics)
        val navTeams = findViewById<ImageButton>(R.id.teams)
        val navAcc = findViewById<ImageButton>(R.id.acc)
        val fragmentManager = supportFragmentManager.beginTransaction()
        when (view) {
            navAcc -> {
                navAnalytics.isSelected = false
                navTeams.isSelected  = false
                navAcc.isSelected = true

                fragmentManager.replace(R.id.frame, accountFragment)
                fragmentManager.commit()
            }
            navTeams -> {
                navAnalytics.isSelected = false
                navTeams.isSelected  = true
                navAcc.isSelected = false

                fragmentManager.replace(R.id.frame, teamFragment)
                fragmentManager.commit()
            }
            navAnalytics -> {
                navAnalytics.isSelected = true
                navTeams.isSelected  = false
                navAcc.isSelected = false

                fragmentManager.replace(R.id.frame, userAnalyticFragment)
                fragmentManager.commit()
            }
        }
    }
}