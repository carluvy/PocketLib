package com.coolbanter.pocketlib.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coolbanter.pocketlib.R
import com.coolbanter.pocketlib.articles.AddArticleActivity
import com.coolbanter.pocketlib.articles.DashboardActivity
import com.coolbanter.pocketlib.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var mAuth : FirebaseAuth
    private lateinit var emailAddress : TextInputEditText
    private lateinit var password : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        password = binding.passwordEt
        emailAddress = binding.emailAddressEt

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            login()
        }



    }

    private fun login() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.text.toString()).matches()) {
            emailAddress.error = "Please enter a valid email address"
            emailAddress.isFocusable
            return

        }
        if (password.text.toString().isEmpty()) {
            password.error = "Please enter a password"
            password.isFocusable
            return

        }
        mAuth.signInWithEmailAndPassword(emailAddress.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Signup failed!", Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }

            }
    }


    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(baseContext, "Please verify your details!", Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(baseContext, "Login failed!", Toast.LENGTH_SHORT).show()
        }


    }
}