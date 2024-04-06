package com.sanyamj138.mealman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanyamj138.mealman.databinding.ActivityPayOutBinding
import com.sanyamj138.mealman.databinding.FragmentMenuBottomSheetBinding

class PayOutActivity : AppCompatActivity() {

    lateinit var binding: ActivityPayOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.PlaceMyOrder.setOnClickListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
    }
}