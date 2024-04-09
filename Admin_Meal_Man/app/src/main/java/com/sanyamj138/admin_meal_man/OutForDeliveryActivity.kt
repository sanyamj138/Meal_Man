package com.sanyamj138.admin_meal_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanyamj138.admin_meal_man.adapter.DeliveryAdapter
import com.sanyamj138.admin_meal_man.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {

    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val customerName = arrayListOf("Mayank Badaulia", "Sanyam Jain", "V Soumya")
        val moneyStatus = arrayListOf("Received", "Not Received", "Pending")
        val adapter = DeliveryAdapter(customerName, moneyStatus)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.deliveryRecyclerView.adapter = adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}