package com.muhsanjaved.wavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.databinding.CartItemBinding

class CartAdapter(
    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private var cartItemDescriptions: MutableList<String>,
    private val cartImages: MutableList<String>,
    private var cartItemQuantitys: MutableList<Int>,
    private var cartItemIngredients: MutableList<String>,

) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // instace Firebase
    private val auth = FirebaseAuth.getInstance()

    init {
        // Inistialize Firebase
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        val cartItemNumber = cartItems.size

        itemQuantities = IntArray(cartItemNumber){1}
        cartItemsReference = database.reference.child("user").child(userId).child("cartItems")
    }

    companion object {
        private var itemQuantities:IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }

//    private val itemQuantities = IntArray(cartItems.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                cartFoodNameTextView.text = cartItems[position]
                carItemPriceTextView.text = cartItemPrices[position]
//                cartImageView.setImageResource(cartImage[position])
                // load image using Glide
                val uriString = cartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImageView)
                quantityTextView.text = "1"

                minusImageButton.setOnClickListener {
                    deceaseQuantity(position)
                }
                plusImageButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deteleImageButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }

            }

        }

        private fun deceaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
//            cartItems.removeAt(position)
//            cartImages.removeAt(position)
//            cartItemPrices.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, cartItems.size)

            val positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve) { uniqueKey->
                if (uniqueKey != null){
                    removeItem(position, uniqueKey)
                }
            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey != null){
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartItemPrices.removeAt(position)
                    cartItemDescriptions.removeAt(position)
                    cartImages.removeAt(position)
                    cartItemQuantitys.removeAt(position)
                    cartItemIngredients.removeAt(position)

                    Toast.makeText(context,"Item Delete",Toast.LENGTH_SHORT).show()

                    // upadte item Quantities
                    itemQuantities = itemQuantities.filterIndexed { index, i -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, cartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show()
                }
            }

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete: (String) -> Unit) {
            cartItemsReference.addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey :String? =null
                        // loop for snapshot children
                    snapshot.children.forEachIndexed {index, dataSnapshot ->
                        if (index == positionRetrieve){
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    uniqueKey?.let { onComplete(it) }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"data no fetch",Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}