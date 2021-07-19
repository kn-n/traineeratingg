package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traineeratingg.Data.Firebase.CURRENT_USER
import com.example.traineeratingg.Data.Firebase.NODE_TEAMS
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT
import com.google.android.gms.tasks.Tasks

class UserAnalyticFragment: Fragment() {

    lateinit var gradesRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<MembersHolder>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member, container, false)
    }

    override fun onResume() {
        super.onResume()

        val topic = view?.findViewById<TextView>(R.id.topic)
        val param1 = view?.findViewById<TextView>(R.id.param1)
        val param2 = view?.findViewById<TextView>(R.id.param2)
        val param3 = view?.findViewById<TextView>(R.id.param3)
        val param4 = view?.findViewById<TextView>(R.id.param4)
        gradesRecyclerView = view?.findViewById(R.id.grades)!!


        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    REF_DATABASE_ROOT.child(NODE_TEAMS).child(user!!.team).addValueEventListener(
                            AppValueEventListener{
                                val team = it.getValue(Team::class.java)
                                topic!!.text = team!!.theme
                            }
                    )
                    REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                            AppValueEventListener{
                                val tasks = it.children.map { it.key }
                                initRecyclerView(tasks as List<String>)
                                for (task in tasks){
                                    REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).child(CURRENT_USER).child(task).addValueEventListener(
                                            AppValueEventListener{
                                                val grades = it.children.map { it.getValue(String::class.java) }
                                                var p1 = 0
                                                var p2 = 0
                                                var p3 = 0
                                                var p4 = 0
                                                for (grade in grades){
                                                    val splitGrade = grade!!.split(" ")
                                                    p1 +=splitGrade[0].toInt()
                                                    p2 +=splitGrade[1].toInt()
                                                    p3 +=splitGrade[2].toInt()
                                                    p4 +=splitGrade[3].toInt()
                                                    context?.let { it1 -> makeToast(it1,splitGrade[3]) }
                                                }
                                                param1!!.text = (p1/tasks.size).toString()
                                                param2!!.text = (p2/tasks.size).toString()
                                                param3!!.text = (p3/tasks.size).toString()
                                                param4!!.text = (p4/tasks.size).toString()
                                            }
                                    )
                                }
                            }
                    )
                }
        )
    }

    private fun initRecyclerView(tasks: List<String>){
        gradesRecyclerView.layoutManager = LinearLayoutManager(context)

        adapter = object : RecyclerView.Adapter<MembersHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.event_evaluation, parent, false)
                return MembersHolder(view)
            }

            override fun onBindViewHolder(holder: MembersHolder, position: Int) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                        AppValueEventListener{
                            val user = it.getValue(User::class.java)
                            REF_DATABASE_ROOT.child(NODE_TEAMS).child(user!!.team).child(NODE_USERS).child(CURRENT_USER).child(tasks[position]).addValueEventListener(
                                    AppValueEventListener{
                                        val marks = it.children.map { it.getValue(String::class.java) }
                                        var p1 = 0
                                        var p2 = 0
                                        var p3 = 0
                                        var p4 = 0
                                        if (marks.isNotEmpty()){
                                            for (mark in marks){
                                                val listMarks = split(mark!!)
                                                p1 += listMarks[0].toInt()
                                                p2 += listMarks[1].toInt()
                                                p3 += listMarks[2].toInt()
                                                p4 += listMarks[3].toInt()
                                            }
                                            holder.param1.text = (p1/marks.size).toString()
                                            holder.param2.text = (p2/marks.size).toString()
                                            holder.param3.text = (p3/marks.size).toString()
                                            holder.param4.text = (p4/marks.size).toString()
                                        }
                                        REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).child(CURRENT_USER).child(tasks[position]).child(CURRENT_USER).addValueEventListener(
                                                AppValueEventListener{
                                                    val myMarks = it.getValue(String::class.java)
                                                    if (myMarks.isNullOrEmpty()){
                                                        holder.evaluate.visibility = View.VISIBLE
                                                        holder.eventName.text = tasks[position]
                                                        holder.evaluate.setOnClickListener {
                                                            replaceFragment(EvaluateFragment(tasks[position], CURRENT_USER))
                                                        }
                                                    } else{
                                                        holder.evaluate.visibility = View.GONE
                                                        var pp1 = 0
                                                        var pp2 = 0
                                                        var pp3 = 0
                                                        var pp4 = 0
                                                        for (mark in marks){
                                                            val listMarks = split(mark!!)
                                                            pp1 += listMarks[0].toInt()
                                                            pp2 += listMarks[1].toInt()
                                                            pp3 += listMarks[2].toInt()
                                                            pp4 += listMarks[3].toInt()
                                                        }
                                                        holder.param1.text = (pp1/marks.size).toString()
                                                        holder.param2.text = (pp2/marks.size).toString()
                                                        holder.param3.text = (pp3/marks.size).toString()
                                                        holder.param4.text = (pp4/marks.size).toString()
                                                        holder.eventName.text = tasks[position]
                                                    }
                                                }
                                        )
                                    }
                            )
                        }
                )
            }

            override fun getItemCount() = tasks.size
        }

        gradesRecyclerView.adapter = adapter
    }

    class MembersHolder (view: View): RecyclerView.ViewHolder(view){
        var eventName: TextView = itemView.findViewById(R.id.event)
        var param1: TextView = itemView.findViewById(R.id.param1)
        var param2: TextView = itemView.findViewById(R.id.param2)
        var param3: TextView = itemView.findViewById(R.id.param3)
        var param4: TextView = itemView.findViewById(R.id.param4)
        var evaluate: Button = itemView.findViewById(R.id.evaluate)
    }
}