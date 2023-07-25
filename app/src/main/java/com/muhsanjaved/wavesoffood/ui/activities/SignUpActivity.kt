package com.muhsanjaved.wavesoffood.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.databinding.ActivityLoginBinding
import com.muhsanjaved.wavesoffood.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding : ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textViewAlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, ChooseYourLocationActivity::class.java)
            startActivity(intent)
        }
    }
}