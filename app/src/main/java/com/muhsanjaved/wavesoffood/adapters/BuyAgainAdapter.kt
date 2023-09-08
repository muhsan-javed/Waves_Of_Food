package com.muhsanjaved.wavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhsanjaved.wavesoffood.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val buyAgainFoodName: List<String>,
    private val buyAgainSellerName: List<String>,
    private val buyAgainPrice: List<String>,
    private val buyAgainImage: List<Int>,
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding =
            BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        val foodName = buyAgainFoodName[position]
        val sellerName = buyAgainSellerName[position]
        val image = buyAgainImage[position]
        val price = buyAgainPrice[position]

        holder.bind(foodName, sellerName, image, price)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, sellerName: String, image: Int, price: String) {
            binding.buyAgainItemFoodNameTextView.text = foodName
            binding.buyAgainItemSellerNameTextView.text = sellerName
            binding.buyAgainItemPriceTextView.text = price
            binding.buyAgainItemImageView.setImageResource(image)
        }

    }
}