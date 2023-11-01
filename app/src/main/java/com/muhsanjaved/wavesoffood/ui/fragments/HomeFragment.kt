package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.MenuAdapter
import com.muhsanjaved.wavesoffood.adapters.PopularAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentHomeBinding
import com.muhsanjaved.wavesoffood.models.MenuItem

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.menuItemTextView.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        retrieveAndDisplayPopularItems()
        return binding.root
    }

    private fun retrieveAndDisplayPopularItems() {
        // get reference to the database
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        // retrieve menu items from the databae
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                Log.d("ITEMS", "onDataChange : Data Received")
                // once data receive set to adapter
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Data Not Fetching",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun randomPopularItems() {
        // Create as shuffled list of menu items Auto
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetMenuItems = index.take(numItemToShow).map { menuItems[it] }

        setPupularItemAdapter(subsetMenuItems)
    }

    private fun setPupularItemAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItems, requireContext())
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1, scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1, scaleType = ScaleTypes.FIT))

        val imageSlide = binding.imageSlider
        imageSlide.setImageList(imageList)
        imageSlide.setImageList(imageList, ScaleTypes.FIT)

        imageSlide.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })

        val foodName = listOf("Burger", "Sandwich", "momo", "Herbal Pancake", "Mixing")

        val price = listOf("$10", "$8", "$15", "$99", "$50")

        val popularFoodImages = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu6
        )
        val adapter = PopularAdapter(foodName, price, popularFoodImages, requireContext())
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }

    companion object {
    }
}