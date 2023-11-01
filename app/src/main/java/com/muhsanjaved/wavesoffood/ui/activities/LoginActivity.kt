package com.muhsanjaved.wavesoffood.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    // Firebase var
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    // login with Google Account
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Firebase Database
        database = Firebase.database.reference
        // Initialize Google Sign In

        val googleSignInClient = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)


        // Login Button
        binding.loginButton.setOnClickListener {
            email = binding.editTextLoginTextEmailAddress.text.toString().trim()
            password = binding.editTextLoginTextPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            } else {
                createUserAccount(email, password)
            }
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
        }

        binding.textViewCreateNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Create a new User Account function
    private fun createUserAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            // Only Login And UpdateUI
            if (it.isSuccessful){
                val user : FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                updateUI(user)
            }
            else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "CreateUserAccount: Authentication Failed", it.exception)
            }
        }

    }

    // GOTO MainActivity with updateUI
    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // Check if user is already logged in
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null)
            updateUI(currentUser)
    }
}