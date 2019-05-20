package com.matias.stindent.views

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.matias.stindent.R
import com.matias.stindent.models.ChatMessage
import com.matias.stindent.registerlogin.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<ViewHolder>(){
    var chatPartnerUser: User? = null

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.latest_message_textview.text = chatMessage.text

        val chatPartner: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartner = chatMessage.toId
        } else {
            chatPartner = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartner")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.latest_message_usernameview.text = chatPartnerUser?.username

                val targetImageView = viewHolder.itemView.imageview_latest_message
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}