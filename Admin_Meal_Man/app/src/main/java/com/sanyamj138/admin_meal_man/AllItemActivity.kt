package com.sanyamj138.admin_meal_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanyamj138.admin_meal_man.adapter.AddItemAdapter
import com.sanyamj138.admin_meal_man.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {

    private val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val menuFoodName = listOf("Pickle", "Khakara", "Thepla")
        val menuItemPrice = listOf("296", "239", "152")
        val menuImage = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
        val adapter = AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice), ArrayList(menuImage))

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter

    }
}