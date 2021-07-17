package com.example.traineeratingg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.traineeratingg.Data.Firebase.*

class EditTeamFragment(val saveOrCreate: String): Fragment() {

    lateinit var chooseMembersRecyclerView: RecyclerView
    lateinit var tasksRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_or_edit_team, container, false)
    }

    override fun onResume() {
        super.onResume()
        val name = view?.findViewById<EditText>(R.id.name)
        val topic = view?.findViewById<EditText>(R.id.topic)
        val searchMember = view?.findViewById<EditText>(R.id.find_member)
        val task = view?.findViewById<EditText>(R.id.task)
        chooseMembersRecyclerView = view?.findViewById(R.id.choose_members)!!
        tasksRecyclerView = view?.findViewById(R.id.delete_tasks)!!
        val addTask = view?.findViewById<ImageButton>(R.id.add_task)
        val save = view?.findViewById<Button>(R.id.save)
        val membersBlock = view?.findViewById<LinearLayout>(R.id.layout1)
        val tasksBlock = view?.findViewById<LinearLayout>(R.id.layout2)

        if (saveOrCreate == "save"){
            if (CURRENT_USER_ROLE=="Практикант"){
                membersBlock!!.visibility = View.GONE
                tasksBlock!!.visibility = View.GONE
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_USER).addValueEventListener(
                        AppValueEventListener{
                            val user = it.getValue(User::class.java)
                            REF_DATABASE_ROOT.child(NODE_TEAMS).child(user!!.team).addValueEventListener(
                                    AppValueEventListener{
                                        val team = it.getValue(Team::class.java)
                                        name!!.setText(team!!.name)
                                        topic!!.setText(team.theme)
                                        save!!.setOnClickListener {
                                            team.name = name.text.toString()
                                            team.theme = topic.text.toString()
                                            REF_DATABASE_ROOT.child(NODE_TEAMS).child(user.team).setValue(team)
                                            replaceFragment(TeamFragment())
                                        }
                                    }
                            )
                        }
                )
            }else{
                membersBlock!!.visibility = View.VISIBLE
                tasksBlock!!.visibility = View.VISIBLE
            }
        }
    }

}