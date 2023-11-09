package com.muhsanjaved.wavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhsanjaved.wavesoffood.databinding.RecentCartItemBinding

class RecentBuyAdapter(
    private var context :Context,
    private var foodNameList :ArrayList<String>,
    private var foodPriceList :ArrayList<String>,
    private var foodImageList :ArrayList<String>,
    private var foodQuantityList :ArrayList<Int>,
): RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentBuyAdapter.RecentViewHolder {
        val binding = RecentCartItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentBuyAdapter.RecentViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = foodNameList.size

    inner class RecentViewHolder(val binding: RecentCartItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                recentFoodName.text = foodNameList[position]
                recentFoodPrice.text = foodPriceList[position]
                recentQuantityCountTextView.text = foodQuantityList[position].toString()
                val uriString = foodImageList[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(recentFoodImage)
            }
        }

    }
}