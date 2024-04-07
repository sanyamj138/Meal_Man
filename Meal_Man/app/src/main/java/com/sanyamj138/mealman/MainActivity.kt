package com.sanyamj138.mealman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sanyamj138.mealman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        var NavController = findNavController(R.id.fragmentContainerView)
        var bottomnav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomnav.setupWithNavController(NavController)

        binding.notificationButton.setOnClickListener {
            val bottomSheetDialog = Notification_Bottom_Fragment()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
    }
}