package com.matias.stindent.registerlogin

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.widget.CheckBox
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.matias.stindent.R
import com.matias.stindent.messages.LatestMessagesActivity
import kotlinx.android.synthetic.main.activity_user_filter.*

class UserFilterActivity : AppCompatActivity() {

    var userInterests = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_filter)

        save_interests_button.setOnClickListener {
            saveInterestsToUser(userInterests)
        }
    }

    fun cardClicked(view: View){
        val checkboxSelected = view as CheckBox
        var cardCell = 0

        when (checkboxSelected.id){
            R.id.row1_colum1_checkbox -> cardCell = 1
            R.id.row1_colum2_checkbox -> cardCell = 2
            R.id.row1_colum3_checkbox -> cardCell = 3
            R.id.row2_colum1_checkbox -> cardCell = 4
            R.id.row2_colum2_checkbox -> cardCell = 5
            R.id.row2_colum3_checkbox -> cardCell = 6
            R.id.row3_colum1_checkbox -> cardCell = 7
            R.id.row3_colum2_checkbox -> cardCell = 8
            R.id.row3_colum3_checkbox -> cardCell = 9
            R.id.row4_colum1_checkbox -> cardCell = 10
            R.id.row4_colum2_checkbox -> cardCell = 11
            R.id.row4_colum3_checkbox -> cardCell = 12
            R.id.row5_colum1_checkbox -> cardCell = 13
            R.id.row5_colum2_checkbox -> cardCell = 14
            R.id.row5_colum3_checkbox -> cardCell = 15
        }

        if (checkboxSelected.isChecked){
            markCardForUser(cardCell, checkboxSelected)
        }
        else{
            unmarkCardForUser(cardCell, checkboxSelected)
        }

    }
    private fun saveInterestsToUser(interests: ArrayList<Int>){
        val user = FirebaseAuth.getInstance().uid ?: ""
        if(user == null) return
        val ref = FirebaseDatabase.getInstance().getReference("/user-interests/$user")
        ref.setValue(interests)
            .addOnSuccessListener {
                Log.d("UserFilter", "successfully saved interests")
                val intent = Intent(this, LatestMessagesActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("UserFilter", "it failed")
            }
    }

    private fun markCardForUser(cardCell: Int, checkBox: CheckBox){
        userInterests.add(cardCell)
        checkBox.setBackgroundColor(Color.GREEN)
        Log.d("UserFilter", "bruker har interesse: " + userInterests.size)

    }
    private fun unmarkCardForUser(cardCell: Int, checkBox: CheckBox){
        userInterests.remove(cardCell)
        checkBox.setBackgroundColor(Color.WHITE)
        Log.d("UserFilter", "bruker tokk bort interesse: " + userInterests.size)
    }
}
