package com.muhsanjaved.wavesoffood.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhsanjaved.wavesoffood.databinding.MenuItemBinding
import com.muhsanjaved.wavesoffood.models.MenuItem
import com.muhsanjaved.wavesoffood.ui.activities.DetailsActivity

class MenuAdapter(
//    private val menuItemsName: MutableList<String>,
//    private val menuItemPrice: MutableList<String>,
//    private val MenuItemImage: MutableList<Int>,

    private val menuItems:List<MenuItem>,
    private val requireContext: Context,

) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]

            // Intent to open details activity and Pass data
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName",menuItem.foodName)
            intent.putExtra("MenuItemPrice",menuItem.foodPrice)
            intent.putExtra("MenuItemDescription",menuItem.foodDescription)
            intent.putExtra("MenuItemIngredient",menuItem.foodIngredient)
            intent.putExtra("MenuItemImage",menuItem.foodImage)
            requireContext.startActivity(intent)  // Start the  details Activity
        }

        // Set data in to recyclerView Items name price image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val url = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(url).into(menuImage)

            }
        }
    }
}
