package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class EvaluateFragment(val taskName: String, val userLogin: String): Fragment() {
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



    }
}