package com.sanyamj138.admin_meal_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanyamj138.admin_meal_man.adapter.PendingOrderAdapter
import com.sanyamj138.admin_meal_man.databinding.ActivityPendingOrderBinding
import com.sanyamj138.admin_meal_man.databinding.PendingOrderItemBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val orderedCustomName = arrayListOf("Mayank Badaulia", "Sanyam Jain", "V Soumya")
        val orderedQuantity = arrayListOf("8", "5", "6")
        val orderedFoodImage = arrayListOf(R.drawable.kakhra, R.drawable.thepla, R.drawable.pickle)
        val adapter = PendingOrderAdapter(orderedCustomName, orderedQuantity, orderedFoodImage, this)
        binding.pendingOrdersRecyclerView.adapter = adapter
        binding.pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}