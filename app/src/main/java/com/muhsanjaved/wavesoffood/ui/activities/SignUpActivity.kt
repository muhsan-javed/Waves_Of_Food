package com.muhsanjaved.wavesoffood.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.muhsanjaved.wavesoffood.R
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

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_Id)).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)


        // Sign up with Google Acoount Button
        binding.googleSignUpbutton.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }

        // Sign up with Facebook Account Button
        binding.facebookSignUpButton.setOnClickListener {

        }

        // GoTo Login Activity
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
                val intent = Intent(this, MainActivity::class.java)
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

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account : GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask->
                    // Successfully sign in with Google
                    if (authTask.isSuccessful){
                        Toast.makeText(this,"Successfully Sign-in with Google 😊", Toast.LENGTH_SHORT).show()
    //                    updateUI(authTask.result?.user)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this,"Sign-in Failed 😒", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else {
            Toast.makeText(this,"Sign-in Failed 😒", Toast.LENGTH_SHORT).show()
        }
    }

}