package com.coolbanter.kikoba.setup.data.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.utils.Utils
import com.coolbanter.kikoba.databinding.ActivitySignUpBinding
import com.coolbanter.kikoba.setup.data.login.LoginActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var name: TextInputEditText
    private lateinit var emailAddress: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUp: MaterialButton
    private lateinit var logIn: MaterialButton
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        mAuth = Utils.getAuth()!!


        name = binding.memberName
        emailAddress = binding.memberEmail
        password = binding.memberPassword
        signUp = binding.btnSignUp
        logIn = binding.btnLogin

        signUp.setOnClickListener {
            signUp()
        }

        logIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }

    private fun signUp() {

        if (name.text.toString().isEmpty()) {
            name.error = "Please enter your name"
            name.isFocusable
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
                        ?.addOnCompleteListener { job ->
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

}