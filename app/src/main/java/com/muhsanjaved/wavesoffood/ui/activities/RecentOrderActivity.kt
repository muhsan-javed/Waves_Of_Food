package com.muhsanjaved.wavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.databinding.ActivityRecentOrderBinding

class RecentOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecentOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecentOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}