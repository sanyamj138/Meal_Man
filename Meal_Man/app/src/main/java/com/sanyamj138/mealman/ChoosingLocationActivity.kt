package com.sanyamj138.mealman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.sanyamj138.mealman.databinding.ActivityChoosingLocationBinding
import com.sanyamj138.mealman.databinding.ActivitySignUpBinding

class ChoosingLocationActivity : AppCompatActivity() {

    private val binding: ActivityChoosingLocationBinding by lazy {
        ActivityChoosingLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf("Jaipur", "Bhopal", "Kota", "Guna")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}