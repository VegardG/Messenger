package com.matias.stindent.messages

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.matias.stindent.R
import com.matias.stindent.registerlogin.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_newmessage.view.*
import java.util.*
import kotlin.collections.ArrayList

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"

        fetchUsers()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    var userInterests = ArrayList<String>()
    var otherUserInterests = ArrayList<String>()

    private fun getUserInterests(){
        val user = FirebaseAuth.getInstance().uid ?: ""
        if(user == null) return
        val ref = FirebaseDatabase.getInstance().getReference("/user-interests/$user")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    userInterests.add(it.toString())
                    Log.d("UserFilter", "added " + it.toString() +"for user: " + user + "to userInterests which now contains " + userInterests.size)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.d("UserFilter", "didnt work man")

            }
        }
        )
    }
    private fun getOtherUserInterests(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-interests")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    otherUserInterests.add(it.toString())
                    Log.d("UserFilter", "added " + it.toString() + "to other users interest")
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }


    private fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        //val refInterests = FirebaseDatabase.getInstance().getReference("/user-interests")
        //getUserInterests()
        //getOtherUserInterests()

        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null){
                        adapter.add(UserItem(user))
                        /*if (!Collections.disjoint(userInterests, otherUserInterests )){
                            adapter.add(UserItem(user))
                        }*/

                    }
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()
                }

                recyclerview_newmessage.adapter = adapter
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_txtview_newmessage.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_newmessagerow)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_newmessage
    }

}
