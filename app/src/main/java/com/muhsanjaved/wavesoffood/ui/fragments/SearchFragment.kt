package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.adapters.MenuAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentSearchBinding
import com.muhsanjaved.wavesoffood.models.MenuItem


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val orignelMenuItems = mutableListOf<MenuItem>()

    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuItemPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

//        adapter = MenuAdapter(
//            filteredMenuFoodName,
//            filteredMenuItemPrice,
//            filteredMenuImage,
//            requireContext()
//        )

//        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.menuRecyclerView.adapter = adapter

        // retrieve Menu Items
        retrieveMenuItem()
        // setup for search view
        setupSearchView()

        // show All menus Item
//        showAllMenu()

        return binding.root
    }

    private fun retrieveMenuItem() {
        // getdatabase retferencer
        database = FirebaseDatabase.getInstance()
        // reference to the menu node
        val foodReferencer :DatabaseReference  = database.reference.child("menu")
        foodReferencer.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnashot in snapshot.children){

                    val menuItem = foodSnashot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        orignelMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {
//        filteredMenuFoodName.clear()
//        filteredMenuItemPrice.clear()
//        filteredMenuImage.clear()
//
//        filteredMenuFoodName.addAll(originalMenuItemFoodName)
//        filteredMenuItemPrice.addAll(originalMenuItemPrice)
//        filteredMenuImage.addAll(originalMenuImages)
        val filteredMenuItem = ArrayList(orignelMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem:  List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuItem, requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }

        })
    }

    private fun filterMenuItems(queue: String) {
        val filteredMenuItem = orignelMenuItems.filter {
            it.foodName?.contains(queue, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItem)
    }

    companion object {
    }
}