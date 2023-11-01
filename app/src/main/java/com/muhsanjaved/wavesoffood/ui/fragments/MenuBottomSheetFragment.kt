package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.adapters.MenuAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.muhsanjaved.wavesoffood.models.MenuItem

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database :FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

       /* val menuFoodName =
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
            MenuAdapter(
                ArrayList(menuFoodName),
                ArrayList(menuPrice),
                ArrayList(menuImages),
                requireContext()
            )
        binding.menuBottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuBottomSheetRecyclerView.adapter = adapter*/
        
        retrieveMenuItems()
        return binding.root
    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef :DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                Log.d("ITEMS", "onDataChange : Data Received")
                // once data receive set to adapter
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Data Not Fetching",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter() {
        if (menuItems.isNotEmpty()){
            val adapter = MenuAdapter(menuItems, requireContext())
            binding.menuBottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuBottomSheetRecyclerView.adapter = adapter
            Log.d("ITEMS", "setAdapter : Data set")
        }
        else{
            Log.d("ITEMS", "setAdapter : Data Not set")
        }
    }

    companion object {

    }
}