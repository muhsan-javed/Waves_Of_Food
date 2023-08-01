package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.BuyAgainAdapter
import com.muhsanjaved.wavesoffood.adapters.MenuAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val historyFoodName =
            listOf(
                "Burger",
                "Sandwich",
                "momo",
                "Herbal Pancake",
                "Mixing",
                "Burger",
                "Burger",
                "Sandwich",
                "momo",
                "Herbal Pancake",
                "Mixing",
                "Burger"
            )

        val historySellerName =
            listOf(
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite",
                "Waroenk Kite"
            )
        val historyPrice =
            listOf("$10", "$8", "$15", "$99", "$50", "$12", "$10", "$8", "$15", "$99", "$50", "$12")

        val historyImages = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu6,
            R.drawable.menu2,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu6,
            R.drawable.menu2
        )

        val adapter =
            BuyAgainAdapter(ArrayList(historyFoodName), ArrayList(historySellerName), ArrayList(historyPrice),ArrayList(historyImages))
        binding.historyBuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.historyBuyAgainRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}