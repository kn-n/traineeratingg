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

        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                AppValueEventListener{
                    val user = it.getValue(User::class.java)
                    if (user!!.team.isNotEmpty()){
                        REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).child(NODE_USERS).addValueEventListener(
                                AppValueEventListener{
                                    val listUsers = it.children.map { it.getValue(User::class.java)!! }
                                    initRecyclerView(listUsers)
                                }
                        )
                    }
                }
        )
    }

    private fun initRecyclerView(members: List<User>){
        membersRecyclerView = view?.findViewById(R.id.members)!!
        membersRecyclerView.layoutManager = LinearLayoutManager(context)

        adapter = object : RecyclerView.Adapter<MembersHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.member, parent, false)
                return MembersHolder(view)
            }

            override fun onBindViewHolder(holder: MembersHolder, position: Int) {
                holder.userName.text = members[position].name
                holder.job.text = members[position].placeOfStudy
            }

            override fun getItemCount() = members.size
        }

        membersRecyclerView.adapter = adapter
    }

    class MembersHolder (view: View): RecyclerView.ViewHolder(view){
        var userName: TextView = itemView.findViewById(R.id.name)
        var job: TextView = itemView.findViewById(R.id.job)
        var img: ImageView = itemView.findViewById(R.id.img)
    }
}


