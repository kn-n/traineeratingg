package com.example.traineeratingg.Data.Firebase

import com.example.traineeratingg.EvaluateFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var CURRENT_USER: String
lateinit var CURRENT_USER_ROLE:String

const val NODE_USERS = "Users"
const val NODE_TEAMS = "Teams"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
}