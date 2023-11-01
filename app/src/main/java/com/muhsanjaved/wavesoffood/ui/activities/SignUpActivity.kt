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
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.muhsanjaved.wavesoffood.databinding.ActivitySignUpBinding
import com.muhsanjaved.wavesoffood.models.UserModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var name :String
    private lateinit var email:String
    private lateinit var password:String
    // Firebase var
    private lateinit var database: DatabaseReference
    private lateinit var auth :FirebaseAuth
    // login with Google Account
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Firebase Database
        database = Firebase.database.reference

//        val googleSignInClient = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)


        binding.textViewAlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Sign up Button code and with Name Email password Using with Firebase Auth
        binding.signUpButton.setOnClickListener {
            // Get Text Form EditText
            name = binding.editTextSignUpTextName.text.toString().trim()
            email = binding.editTextSignUpTextEmailAddress.text.toString().trim()
            password = binding.editTextSignUpTextPassword.text.toString().trim()

            if (name.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Please fill All Details", Toast.LENGTH_LONG).show()
            }
            else{
                // Create new Account Funcatipn
                createAccount(email,password)
            }

        }
    }

    // Create new Account function with Firebase Auth
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, ChooseYourLocationActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failed", task.exception)
            }
        }
    }

    // Save data into Firebase RealTime Database DB
    private fun saveUserData() {
        // Get Text form EditText
        name = binding.editTextSignUpTextName.text.toString().trim()
        email = binding.editTextSignUpTextEmailAddress.text.toString().trim()
        password = binding.editTextSignUpTextPassword.text.toString().trim()

        val user = UserModel(name,email,password)
        val userId :String = FirebaseAuth.getInstance().currentUser!!.uid
        // Save User Data Firebase Database
        database.child("user").child(userId).setValue(user)
    }
}