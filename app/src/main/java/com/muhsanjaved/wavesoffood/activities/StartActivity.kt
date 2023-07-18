package com.muhsanjaved.wavesoffood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding : ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}