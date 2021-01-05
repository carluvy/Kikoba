package com.coolbanter.kikoba.setup.data.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.coolbanter.kikoba.views.ListActivity

import com.coolbanter.kikoba.R
import com.coolbanter.kikoba.utils.Utils
import com.coolbanter.kikoba.databinding.ActivityLoginBinding
import com.coolbanter.kikoba.setup.data.signup.SignUpActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

//    companion object {
//        const val SIGN_IN_REQUEST_CODE = 100
//    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailAddress: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var login: MaterialButton
    private lateinit var signUp: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        mAuth = Utils.getAuth()!!

        emailAddress = binding.emailAddressEt
        password = binding.passwordEt
        login = binding.btnLogin
        signUp = binding.btnSignUp

        login.setOnClickListener {
            login()
        }
        signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
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
            .addOnCompleteListener { task ->
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
                startActivity(Intent(this, ListActivity::class.java))
                finish()
            } else {
                Toast.makeText(baseContext, "Please verify your details!", Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(baseContext, "Login failed!", Toast.LENGTH_SHORT).show()
        }


    }

}
