package com.muhsanjaved.wavesoffood.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.values
import com.muhsanjaved.wavesoffood.databinding.ActivityDetailsBinding
import com.muhsanjaved.wavesoffood.models.CartItems

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var foodName:String? =null
    private var foodPrice:String? =null
    private  var foodDescription:String? =null
    private  var foodIngredient:String? =null
    private var foodImage : String? = null

    private lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize Firabase Auth
        auth = FirebaseAuth.getInstance()

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

        //
        binding.detailAddToCartButton.setOnClickListener {
            addItemToCart()
        }

    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        // Create  a CartItems objects
        val cartItem = CartItems(foodName.toString(),foodPrice.toString(), foodDescription.toString(),foodImage.toString(), 1 )

        // Save Data to cart item to firebase database
        database.child("user").child(userId).child("cartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this,"Items Added into cart successfully 🥰",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Item Not Added 😒",Toast.LENGTH_SHORT).show()
            }
    }
}