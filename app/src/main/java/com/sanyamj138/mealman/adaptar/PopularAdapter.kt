package com.sanyamj138.mealman.adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanyamj138.mealman.DetailsActivity
import com.sanyamj138.mealman.databinding.PopularItemsBinding

class PopularAdapter (private val items:List<String>, private val price:List<String>,  private val image:List<Int>, private val requireContext: Context) : RecyclerView.Adapter<PopularAdapter.PouplerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PouplerViewHolder {
        return PouplerViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PouplerViewHolder, position: Int) {
        val item = items[position]
        val price = price[position]
        val images = image[position]
        holder.bind(item, price, images)

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PouplerViewHolder (private val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        private val imagesView = binding.imageView2

        fun bind(item: String, price: String, images: Int) {
            binding.foodNamePopular.text = item
            binding.pricePopular.text = price
            imagesView.setImageResource(images)
        }
    }
}