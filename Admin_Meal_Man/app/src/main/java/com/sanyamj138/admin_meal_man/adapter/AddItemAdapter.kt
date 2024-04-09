package com.sanyamj138.admin_meal_man.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanyamj138.admin_meal_man.databinding.ItemItemBinding

class AddItemAdapter(private val MenuItemName: ArrayList<String>, private val MenuItemPrice: ArrayList<String>, private val MenuItemImage: ArrayList<Int>) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(MenuItemName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemAdapter.AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemAdapter.AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                foodNameTextView.text = MenuItemName[position]
                PriceTextView.text = MenuItemPrice[position]
                foodImageView.setImageResource(MenuItemImage[position])

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun decreaseQuantity(position: Int) {
            if(itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun increaseQuantity(position: Int) {
            if(itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemImage.removeAt(position)
            MenuItemPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemName.size)
        }

    }
}