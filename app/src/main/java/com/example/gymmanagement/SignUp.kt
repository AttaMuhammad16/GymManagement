package com.example.gymmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signup: CardView
    private lateinit var loginActivity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.signup_email)
        password = findViewById(R.id.signup_password)
        signup = findViewById(R.id.signup)
        loginActivity = findViewById(R.id.login_activity)

        signup.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                showToast("Please enter email and password")
            } else {
                auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            showToast("User created successfully")
                            startActivity(Intent(this,Login::class.java))
                        } else {
                            showToast("User not created: ${task.exception?.message}")
                      }
                 }
            }
        }
        loginActivity.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
