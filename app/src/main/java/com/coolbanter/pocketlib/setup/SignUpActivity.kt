package com.coolbanter.pocketlib.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coolbanter.pocketlib.R
import com.coolbanter.pocketlib.databinding.ActivitySignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth



class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userName : TextInputEditText
    private lateinit var emailAddress : TextInputEditText
    private lateinit var password : TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()

        userName = binding.nameEt
        password = binding.passwordEt
        emailAddress = binding.emailAddressEt

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }

        binding.btnSignUp.setOnClickListener {
            signUp()
        }




//            val profile = Profile(userName, emailAddress)
//            saveProfile(profile)
//            registerUser()


        }

    private fun signUp() {

        if (userName.text.toString().isEmpty()) {
            userName.error = "Please enter your name"
            userName.isFocusable
            return
        }
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
        mAuth.createUserWithEmailAndPassword(emailAddress.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener{job ->
                            if (job.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }

            } else {
                Toast.makeText(baseContext, "Signup failed!", Toast.LENGTH_SHORT).show()

            }



    }



    }

//    private fun registerUser() {
//        if (emailAddress.isNotEmpty() && password.isNotEmpty()) {
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    mAuth.createUserWithEmailAndPassword(emailAddress, password)
//                    withContext(Dispatchers.Main) {
//                        checkLoggedInState()
//                    }
//                } catch (ex : Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@SignUpActivity, ex.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            }
//
//        }
//    }
//
//        private fun saveProfile(profile: Profile)= CoroutineScope(Dispatchers.IO).launch {
//            try {
//                profileCollectionRef.add(profile).await()
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@SignUpActivity,
//                        "Profile saved successfully",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(this@SignUpActivity, "${ex.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//    override fun onStart() {
//        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
//        super.onStart()
//    }
//
//    private fun updateUI(currentUser: FirebaseUser?) {
//
//    }
//
//
//    private fun checkLoggedInState() {
//        if (mAuth.currentUser == null) {
//            Toast.makeText(this, "You don't have an account", Toast.LENGTH_LONG).show()
//
//
//        } else {
//            Toast.makeText(this, "You have an account", Toast.LENGTH_LONG).show()
////            launchActivity<LoginActivity>()
////            finish()
//        }
//    }

}
