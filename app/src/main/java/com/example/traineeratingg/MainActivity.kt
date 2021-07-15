package com.example.traineeratingg

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        navAnalytics.setOnClickListener {
            navAnalytics.isSelected = true
            navTeams.isSelected  = false
            navAcc.isSelected = false

            fragmentManager.add(R.id.frame, userAnalyticFragment)
        }

        navAcc.setOnClickListener {
            navAnalytics.isSelected = false
            navTeams.isSelected  = false
            navAcc.isSelected = true

            fragmentManager.add(R.id.frame, accountFragment)
        }
        fragmentManager.commit()
    }
}