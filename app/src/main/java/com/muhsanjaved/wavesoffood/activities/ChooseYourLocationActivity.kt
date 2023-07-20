package com.muhsanjaved.wavesoffood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import com.muhsanjaved.wavesoffood.MainActivity
import com.muhsanjaved.wavesoffood.R
import com.muhsanjaved.wavesoffood.databinding.ActivityChooseYourLocationBinding

class ChooseYourLocationActivity : AppCompatActivity() {

    private val binding : ActivityChooseYourLocationBinding by lazy {
        ActivityChooseYourLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf("Qambar", "Larkana", "Brohi Mohalla", "Ali Khan Kamber")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }
}