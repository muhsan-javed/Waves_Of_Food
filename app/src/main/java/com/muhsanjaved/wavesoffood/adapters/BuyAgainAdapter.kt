package com.muhsanjaved.wavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhsanjaved.wavesoffood.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val buyAgainFoodName: MutableList<String>,
//    private val buyAgainSellerName: MutableList<String>,
    private val buyAgainFoodPrice: MutableList<String>,
    private val buyAgainFoodImage: MutableList<String>,
    private var requireContext:Context
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding =
            BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
//        val foodName = buyAgainFoodName[position]
////        val sellerName = buyAgainSellerName[position]
//        val price = buyAgainPrice[position]
//        val image = buyAgainImage[position]
//
////        holder.bind(foodName, sellerName, image, price)
//        holder.bind(foodName, price, image)
        holder.bind(
            buyAgainFoodName[position],
            buyAgainFoodPrice[position],
            buyAgainFoodImage[position]
        )
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: String) {
            binding.buyAgainItemFoodNameTextView.text = foodName
            binding.buyAgainItemPriceTextView.text = foodPrice
            val uriString = foodImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainItemImageView)
        }
        /*fun bind(foodName: String, sellerName: String, image: Int, price: String) {
            binding.buyAgainItemFoodNameTextView.text = foodName
            binding.buyAgainItemSellerNameTextView.text = sellerName
            binding.buyAgainItemPriceTextView.text = price
            binding.buyAgainItemImageView.setImageResource(image)
        }
*/
    }
}