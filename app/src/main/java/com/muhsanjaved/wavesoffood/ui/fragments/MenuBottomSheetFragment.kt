package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.MenuAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentMenuBottomSheetBinding

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding

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
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            dismiss()
        }

        val menuFoodName =
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
        val menuPrice =
            listOf("$10", "$8", "$15", "$99", "$50", "$12", "$10", "$8", "$15", "$99", "$50", "$12")

        val menuImages = listOf(
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
            MenuAdapter(ArrayList(menuFoodName), ArrayList(menuPrice), ArrayList(menuImages))
        binding.menuBottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuBottomSheetRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}