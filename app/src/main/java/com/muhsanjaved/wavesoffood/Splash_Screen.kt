package com.muhsanjaved.wavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.muhsanjaved.wavesoffood.activities.StartActivity

@Suppress("DEPRECATION")
class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this,StartActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}