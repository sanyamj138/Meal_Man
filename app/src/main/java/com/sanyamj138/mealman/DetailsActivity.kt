package com.sanyamj138.mealman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanyamj138.mealman.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getIntExtra("MenuItemImage", 0)
        binding.detailFoodName.text = foodName
        binding.DetailFoodImage.setImageResource(foodImage)

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}