package com.muhsanjaved.wavesoffood.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.muhsanjaved.wavesoffood.databinding.ActivityChooseYourLocationBinding
import com.muhsanjaved.wavesoffood.ui.MainActivity

class ChooseYourLocationActivity : AppCompatActivity() {

    private val binding: ActivityChooseYourLocationBinding by lazy {
        ActivityChooseYourLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf("Qambar", "Larkana", "Brohi Mohalla", "Ali Khan Kamber","karachi","Lahore")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

        binding.desTextView.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}