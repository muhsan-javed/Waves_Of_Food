package com.muhsanjaved.wavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.wavesoffood.databinding.ActivityPayoutBinding
import com.muhsanjaved.wavesoffood.ui.fragments.CongratsBottomSheetFragment

class PayoutActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityPayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.placeMyOrderButton.setOnClickListener {
            val bottomSheetDialog = CongratsBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }
    }
}