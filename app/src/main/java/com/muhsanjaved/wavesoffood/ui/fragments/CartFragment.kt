package com.muhsanjaved.wavesoffood.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.CartAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentCartBinding
import com.muhsanjaved.wavesoffood.ui.activities.PayoutActivity

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)


        val cartFoodName =
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
        binding.cartRecyclerView.adapter = adapter

        binding.cartProceedButton.setOnClickListener {
            val intent = Intent(requireContext(), PayoutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {
    }
}