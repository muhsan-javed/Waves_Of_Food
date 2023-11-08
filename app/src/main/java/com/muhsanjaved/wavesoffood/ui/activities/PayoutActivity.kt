package com.muhsanjaved.wavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.databinding.ActivityPayoutBinding
import com.muhsanjaved.wavesoffood.ui.fragments.CongratsBottomSheetFragment

class PayoutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPayoutBinding
    private lateinit var name :String
    private lateinit var address:String
    private lateinit var phoneNumber:String
    private lateinit var totalAmount:String

    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId :String

    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredient: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemQuantities: ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = ActivityPayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initiaz Firebase and USer Details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        setUserData()

        // Get user details form Firebase
        foodItemName = intent.getStringArrayListExtra("foodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("foodItemPrice") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("foodItemDescription") as ArrayList<String>
        foodItemIngredient = intent.getStringArrayListExtra("foodItemIngredient") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("foodItemImage") as ArrayList<String>
        foodItemQuantities = intent.getIntegerArrayListExtra("foodItemQuantities") as ArrayList<Int>

        totalAmount = calculateTotalAmount().toString() + "$"

        binding.payoutTotalAmount.isEnabled = false
        binding.payoutTotalAmount.text = totalAmount

        binding.placeMyOrderButton.setOnClickListener {
            val bottomSheetDialog = CongratsBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }
        binding.payoutBackButton.setOnClickListener {
            finish()
        }
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until foodItemPrice.size){
            var price = foodItemPrice[i]
            val lastChar = price.last()
            val priceIntValue = if (lastChar == '$') {
                price.dropLast(1).toInt()
            }else {
                price.toInt()
            }
            var quantity = foodItemQuantities[i]
            totalAmount += priceIntValue *quantity
        }

        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null){
            val userId = user.uid
            val userReferencer = databaseReference.child("user").child(userId)

            userReferencer.addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if(snapshot.exists()){
                        val name = snapshot.child("name").getValue(String::class.java)?:""
                        val address = snapshot.child("address").getValue(String::class.java)?:""
                        val phoneNumber = snapshot.child("phone").getValue(String::class.java)?:""

                        binding.apply {
                            payoutName.setText(name)
                            payoutAddress.setText(address)
                            payoutPhoneNumber.setText(phoneNumber)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}