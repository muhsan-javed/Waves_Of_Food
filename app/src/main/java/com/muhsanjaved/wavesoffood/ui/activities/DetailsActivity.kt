package com.muhsanjaved.wavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.wavesoffood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getIntExtra("MenuItemImage", 1)

        binding.detailFoodNameTextView.text = foodName

        binding.detailImageView.setImageResource(foodImage)

        binding.detailGoToBackImageButton.setOnClickListener {
            finish()
        }
        binding.detailAddToCartButton.setOnClickListener {

        }

    }
}