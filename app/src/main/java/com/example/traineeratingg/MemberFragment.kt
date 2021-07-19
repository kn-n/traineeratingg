package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traineeratingg.Data.Firebase.CURRENT_USER
import com.example.traineeratingg.Data.Firebase.NODE_TEAMS
import com.example.traineeratingg.Data.Firebase.NODE_USERS
import com.example.traineeratingg.Data.Firebase.REF_DATABASE_ROOT

class MemberFragment(val userLogin: String): Fragment() {

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
        gradesRecyclerView = view?.findViewById(R.id.grades)!!

        REF_DATABASE_ROOT.child(NODE_USERS).child(userLogin).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    REF_DATABASE_ROOT.child(NODE_TEAMS).child(user!!.team).addValueEventListener(
                            AppValueEventListener{
                                val team = it.getValue(Team::class.java)
                                topic!!.text = team!!.theme
                            }
                    )
                    REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).child(userLogin).addValueEventListener(
                            AppValueEventListener{
                                val tasks = it.children.map { it.key }
                                initRecyclerView(tasks as List<String>)
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
                holder.grades.visibility = View.GONE
                REF_DATABASE_ROOT.child(NODE_USERS).child(userLogin).addValueEventListener(
                        AppValueEventListener{
                            val user = it.getValue(User::class.java)
                            REF_DATABASE_ROOT.child(NODE_TEAMS).child(user!!.team).child(NODE_USERS).child(userLogin).child(tasks[position]).addValueEventListener(
                                    AppValueEventListener{
                                        REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).child(userLogin).child(tasks[position]).child(CURRENT_USER).addValueEventListener(
                                                AppValueEventListener{
                                                    val myMarks = it.getValue(String::class.java)
                                                    if (myMarks.isNullOrEmpty()){
                                                        holder.evaluate.visibility = View.VISIBLE
                                                        holder.eventName.text = tasks[position]
                                                        holder.evaluate.setOnClickListener {
                                                            replaceFragment(EvaluateFragment(tasks[position], userLogin))
                                                        }
                                                    } else{
                                                        holder.evaluate.visibility = View.GONE
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
        var grades: FrameLayout = itemView.findViewById(R.id.grades)
        var evaluate: Button = itemView.findViewById(R.id.evaluate)
    }
}