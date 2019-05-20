package com.matias.stindent.registerlogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.matias.stindent.R
import com.matias.stindent.messages.LatestMessagesActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_editxt_login.text.toString()
            val passord = passord_editxt_login.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, passord)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("LoginActivity", "Login successful")
                        updateUI()
                    } else {
                        Log.d("LoginActivity", "Login failed")
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        backToRegister_txtview_login.setOnClickListener {
            finish()
        }

    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, LatestMessagesActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}