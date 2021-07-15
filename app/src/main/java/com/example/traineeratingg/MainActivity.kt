package com.example.traineeratingg

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

private val teamFragment = TeamFragment()
private val userAnalyticFragment = UserAnalyticFragment()
private val accountFragment = AccountFragment()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navAnalytics = findViewById<ImageButton>(R.id.analytics)
        navAnalytics.isSelected = false
        val navTeams = findViewById<ImageButton>(R.id.teams)
        navTeams.isSelected  = true
        val navAcc = findViewById<ImageButton>(R.id.acc)
        navAcc.isSelected = false

        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(R.id.frame, teamFragment)
        fragmentManager.commit()
    }

    override fun onResume() {
        super.onResume()

        val navAnalytics = findViewById<ImageButton>(R.id.analytics)
        navAnalytics.isSelected = false
        val navTeams = findViewById<ImageButton>(R.id.teams)
        navTeams.isSelected  = true
        val navAcc = findViewById<ImageButton>(R.id.acc)
        navAcc.isSelected = false

        navAnalytics.setOnClickListener {
            commitFragment(navAnalytics)
        }

        navAcc.setOnClickListener {
            commitFragment(navAcc)
        }

        navTeams.setOnClickListener {
            commitFragment(navTeams)
        }

    }

    fun commitFragment(view: View){
        val navAnalytics = findViewById<ImageButton>(R.id.analytics)
        val navTeams = findViewById<ImageButton>(R.id.teams)
        val navAcc = findViewById<ImageButton>(R.id.acc)
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