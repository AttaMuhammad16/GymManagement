package com.example.gymmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var login:CardView
    private lateinit var forgotPassword:TextView
    private lateinit var signupActivity:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        auth= FirebaseAuth.getInstance()

        if (auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }

        email=findViewById(R.id.login_email)
        password=findViewById(R.id.login_password)
        login=findViewById(R.id.login)
        forgotPassword=findViewById(R.id.forgotPassword)
        signupActivity=findViewById(R.id.signup_activity)

        login.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                showToast("Please enter email and password")
            } else {
                auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this,MainActivity::class.java))
                        } else {
                            showToast("wrong email and password")
                        }
                  }
            }
        }
        signupActivity.setOnClickListener {
           val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}