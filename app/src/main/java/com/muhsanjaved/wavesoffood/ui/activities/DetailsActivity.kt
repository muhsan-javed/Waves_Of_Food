package com.muhsanjaved.wavesoffood.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muhsanjaved.wavesoffood.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var foodName:String? =null
    private var foodPrice:String? =null
    private  var foodDescription:String? =null
    private  var foodIngredient:String? =null
    private var foodImage : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodName = intent.getStringExtra("MenuItemName")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredient = intent.getStringExtra("MenuItemIngredient")
        foodImage = intent.getStringExtra("MenuItemImage")


        with(binding){
            detailFoodNameTextView.text = foodName
            detailsShortDescriptionTextView.text = foodDescription
            detailIngredientsTextView.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailImageView)
        }

        binding.detailGoToBackImageButton.setOnClickListener {
            finish()
        }
        binding.detailAddToCartButton.setOnClickListener {

        }

    }
}