package com.muhsanjaved.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhsanjaved.wavesoffood.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val itemFoodName: List<String>,
    private val itemSellerName: List<String>,
    private val itemPrice: List<String>,
    private val itemImage: List<Int>,
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        val foodName = itemFoodName[position]
        val sellerName = itemSellerName[position]
        val image = itemImage[position]
        val price = itemPrice[position]
        holder.bind(foodName, sellerName, image,price)
    }
    override fun getItemCount(): Int = itemFoodName.size

    class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, sellerName: String, image: Int, price: String) {
            binding.buyAgainItemFoodNameTextView.text = foodName
            binding.buyAgainItemSellerNameTextView.text = sellerName
            binding.buyAgainItemPriceTextView.text = price
            binding.buyAgainItemImageView.setImageResource(image)
        }

    }
}