package com.muhsanjaved.wavesoffood.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muhsanjaved.wavesoffood.databinding.FragmentCongratsBottomSheetBinding
import com.muhsanjaved.wavesoffood.ui.activities.MainActivity

class CongratsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCongratsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCongratsBottomSheetBinding.inflate(inflater,container,false)

        binding.goBackHomeButton.setOnClickListener {
            //dismiss()
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireContext().startActivity(intent)

        }
        return binding.root
    }
}