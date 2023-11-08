package com.muhsanjaved.wavesoffood.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.BuyAgainAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentHistoryBinding
import com.muhsanjaved.wavesoffood.models.OrderDetails
import com.muhsanjaved.wavesoffood.ui.activities.RecentOrderActivity

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId:String
    private var listOfOrderItem:MutableList<OrderDetails> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Retrieve and  display the user order History
        retrieveBuyHistory()

        // recent buy Button click
        binding.recentBuyItem.setOnClickListener {
            seeItemsRecentBuy()
        }
//        setupRecyclerView()
        return binding.root
    }

    // Funcation to see items recent buy
    private fun seeItemsRecentBuy() {
        listOfOrderItem.firstOrNull()?.let { recentBuy->
            val intent = Intent(requireContext(), RecentOrderActivity::class.java)
            intent.putExtra("RecentBuyOrderItem",recentBuy)
            startActivity(intent)
        }
    }

    // Function to see items  buy history
    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid?:""

        val buyItemReference :DatabaseReference = database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children){
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }

                //
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()){
                    // DIsplay the most recent order details
                    setDataInRecentBuyItem()
                    // setup the recyclerView  ithe previuse order details
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    // Function to Display the most recent order details
    private fun setDataInRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding){
                historyOrderedFoodName.text = it.foodNames?.firstOrNull()?:""
                historyOrderedFoodPrice.text = it.foodPrices?.firstOrNull()?:""
                val image = it.foodImage?.firstOrNull()?:""
                val uri  = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(historyOrderedImageView)

                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()){

                }
            }
        }
    }

    // Function to setup the recyclerView  the previous order details
    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage =  mutableListOf<String>()

        for (i in 1 until listOfOrderItem.size){
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { buyAgainFoodName.add(it) }
            listOfOrderItem[i].foodPrices?.firstOrNull()?.let { buyAgainFoodPrice.add(it) }
            listOfOrderItem[i].foodImage?.firstOrNull()?.let { buyAgainFoodImage.add(it) }
        }
        val rv = binding.historyBuyAgainRecyclerView
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage,requireContext())
        rv.adapter = buyAgainAdapter
    }

  /*  private fun setupRecyclerView() {
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

        val adapter = BuyAgainAdapter(ArrayList(historyFoodName),ArrayList(historySellerName),ArrayList(historyPrice),ArrayList(historyImages))
        binding.historyBuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.historyBuyAgainRecyclerView.adapter = adapter

    }

    companion object {

    }*/
}