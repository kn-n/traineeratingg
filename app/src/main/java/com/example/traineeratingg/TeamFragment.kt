package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traineeratingg.Data.Firebase.*
import org.w3c.dom.Text

class TeamFragment: Fragment() {

    lateinit var membersRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<MembersHolder>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onResume() {
        super.onResume()

        val date = view?.findViewById<TextView>(R.id.date)
        val teamName = view?.findViewById<TextView>(R.id.team_name)
        val topic = view?.findViewById<TextView>(R.id.topic)

        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    if (user!!.team.isNotEmpty()){
                        REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).addValueEventListener(
                                AppValueEventListener{
                                    val team = it.getValue(Team::class.java)
                                    date!!.text = team!!.date
                                    teamName!!.text = team.name
                                    topic!!.text = team.theme
                                }
                        )
                        REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).addValueEventListener(
                                AppValueEventListener{
                                    val listUsers = it.children.map { it.key }
                                    initRecyclerView(listUsers as List<String>)
                                }
                        )
                    }
                }
        )
    }

    private fun initRecyclerView(members: List<String>){
        membersRecyclerView = view?.findViewById(R.id.members)!!
        membersRecyclerView.layoutManager = LinearLayoutManager(context)

        adapter = object : RecyclerView.Adapter<MembersHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.member, parent, false)
                return MembersHolder(view)
            }

            override fun onBindViewHolder(holder: MembersHolder, position: Int) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(members[position]).addValueEventListener(
                        AppValueEventListener{
                            val user = it.getValue(User::class.java)
                            holder.userName.text = user!!.name
                            holder.job.text = user.placeOfStudy
                        }
                )
            }

            override fun getItemCount() = members.size
        }

        membersRecyclerView.adapter = adapter
    }

    class MembersHolder (view: View): RecyclerView.ViewHolder(view){
        var userName: TextView = itemView.findViewById(R.id.name)
        var job: TextView = itemView.findViewById(R.id.job)
    }
}


