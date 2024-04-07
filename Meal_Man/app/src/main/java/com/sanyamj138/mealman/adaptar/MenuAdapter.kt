package com.sanyamj138.mealman.adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanyamj138.mealman.DetailsActivity
import com.sanyamj138.mealman.databinding.MenuItemBinding

class MenuAdapter(private var menuItemsName: MutableList<String>, private var menuItemPrice: MutableList<String>, private var MenuImage: MutableList<Int>, private val requireContext: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemClickListener: OnClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemsName.size

    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)
                }
                val intent = Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", menuItemsName[position])
                intent.putExtra("MenuItemImage", MenuImage[position])
                requireContext.startActivity(intent)
            }
        }

        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text = menuItemsName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(MenuImage[position])
            }
        }
    }
    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}