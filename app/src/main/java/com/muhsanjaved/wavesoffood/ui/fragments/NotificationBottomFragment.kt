package com.muhsanjaved.wavesoffood.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.adapters.CartAdapter
import com.muhsanjaved.wavesoffood.adapters.MenuAdapter
import com.muhsanjaved.wavesoffood.adapters.NotificationAdapter
import com.muhsanjaved.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.muhsanjaved.wavesoffood.databinding.FragmentNotificationBottomBinding

class NotificationBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationBottomBinding

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
        binding = FragmentNotificationBottomBinding.inflate(inflater, container, false)

        val notifications = listOf(
            "Your order has been Canceled Successfully",
            "Order has been taken by the driver", "Congrats Your Order Placed"
        )
        val notificationImages = listOf(
            R.drawable.sademoji,
            R.drawable.buss,
            R.drawable.sucess
        )
        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImages)
        )

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}