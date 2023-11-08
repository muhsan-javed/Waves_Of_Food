package com.muhsanjaved.wavesoffood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.CartAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentCartBinding
import com.muhsanjaved.wavesoffood.models.CartItems
import com.muhsanjaved.wavesoffood.ui.activities.PayoutActivity

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        /*val cartFoodName =
            listOf("Burger", "Sandwich", "momo", "Herbal Pancake", "Mixing", "Burger")
        val cartPrice = listOf("$10", "$8", "$15", "$99", "$50", "$12")
        val foodImages = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu6,
            R.drawable.menu2
        )

        val adapter =
            CartAdapter(ArrayList(cartFoodName), ArrayList(cartPrice), ArrayList(foodImages))
              binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter*/

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()


        binding.cartProceedButton.setOnClickListener {
            // Get order items details before processding to check out
            getOrderItemsDetail()
//            val intent = Intent(requireContext(), PayoutActivity::class.java)
//            startActivity(intent)
        }

        return binding.root
    }

    private fun getOrderItemsDetail() {

        val orderIdReference : DatabaseReference = database.reference.child("user").child(userId).child("cartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
        val foodImage = mutableListOf<String>()

        // Get Items Quantities
        val foodQuantities = cartAdapter.getUpDatedItemsQuanities()

        orderIdReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (foodSnapshot in snapshot.children){
                    // Get the cartItems to respective list
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    // Add itesm details in list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodIngredients?.let { foodIngredient.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                }

                orderNow(foodName, foodPrice, foodDescription, foodIngredient, foodImage, foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(requireContext(),"Order Making failed. Please Tray Again", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodImage: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null){
            val intent = Intent(requireContext(), PayoutActivity::class.java)
            intent.putExtra("foodItemName", foodName as ArrayList<String>)
            intent.putExtra("foodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("foodItemDescription", foodDescription as ArrayList<String>)
            intent.putExtra("foodItemIngredient", foodIngredient as ArrayList<String>)
            intent.putExtra("foodItemImage", foodImage as ArrayList<String>)
            intent.putExtra("foodItemQuantities", foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {

        // database referencer to thw Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""
        val foodReferencer : DatabaseReference = database.reference.child("user").child(userId).child("cartItems")

        // list to store cart items
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodImageUri = mutableListOf()
        quantity = mutableListOf()

        // fetch data fromthe database
        foodReferencer.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    // get the cartItemes object from thee child node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)

                    // add cart items details to the list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodIngredients?.let { foodIngredients.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(requireContext(),"data no fetch",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        cartAdapter = CartAdapter(
            requireContext(),foodNames,foodPrices,foodDescriptions,foodImageUri,quantity,foodIngredients
        )
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartRecyclerView.adapter = cartAdapter
    }

    companion object {
    }
}