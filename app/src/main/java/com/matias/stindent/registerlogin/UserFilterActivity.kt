package com.matias.stindent.registerlogin

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.View
import com.matias.stindent.R
import kotlinx.android.synthetic.main.activity_user_filter.*

class UserFilterActivity : AppCompatActivity() {

    var userInterests = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_filter)

        row1_colum1_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                row1_colum1_cardview.setCardBackgroundColor(Color.GREEN)
            }else{
                row1_colum1_cardview.setCardBackgroundColor(Color.WHITE)
            }


        }


    }
    var isClicked = 0


    fun cardClicked(view: View){
        val cardSelected = view as CardView
        var cardCell = 0

        when (cardSelected.id){
            R.id.row1_colum1_checkbox -> cardCell = 1
            R.id.row1_colum2_cardview -> cardCell = 2
            R.id.row1_colum3_cardview -> cardCell = 3
            R.id.row2_colum1_cardview -> cardCell = 4
            R.id.row2_colum2_cardview -> cardCell = 5
            R.id.row2_colum3_cardview -> cardCell = 6
            R.id.row3_colum1_cardview -> cardCell = 7
            R.id.row3_colum2_cardview -> cardCell = 8
            R.id.row3_colum3_cardview -> cardCell = 9
            R.id.row4_colum1_cardview -> cardCell = 10
            R.id.row4_colum2_cardview -> cardCell = 11
            R.id.row4_colum3_cardview -> cardCell = 12
            R.id.row5_colum1_cardview -> cardCell = 13
            R.id.row5_colum2_cardview -> cardCell = 14
            R.id.row5_colum3_cardview -> cardCell = 15
        }

        if (isClicked == 0){
            markCardForUser(cardCell, cardSelected)
        }
        else{
            unmarkCardForUser(cardCell, cardSelected)
        }

        //cardAddedToUser(cardCell, cardSelected, isClicked)


    }
    private fun markCardForUser(cardCell: Int, cardSelected: CardView){
        userInterests.add(cardCell)
        cardSelected.setCardBackgroundColor(Color.GREEN)
        isClicked = 1

    }
    private fun unmarkCardForUser(cardCell: Int, cardSelected: CardView){
        userInterests.remove(cardCell)
        cardSelected.setCardBackgroundColor(Color.WHITE)
        isClicked = 1
    }




    /*private fun cardAddedToUser(cardCell: Int, cardSelected: CardView, isClicked: Int){
        var n = cardCell

        if(n == 1) {
            cardSelected.setCardBackgroundColor(Color.GREEN)
            userInterests.add(cardCell)
            isClicked + 1
        }else if(isClicked == 1){
            cardSelected.setCardBackgroundColor(Color.WHITE)
            userInterests.remove(cardCell)
            isClicked - 1
        }

    }*/
}
