package com.sanyamj138.mealman.adaptar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanyamj138.mealman.DetailsActivity
import com.sanyamj138.mealman.databinding.MenuItemBinding
import com.sanyamj138.mealman.model.MenuItem

class MenuAdapter(
    private val menuItems:List<MenuItem>,
    private val requiredContext : Context
): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    openDetailActivity(position)
                }

            }
        }

        // set data into recyclerview item name, price, image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requiredContext).load(uri).into(menuImage)
            }
        }

    }

    private fun openDetailActivity(position: Int) {
        val menuItem = menuItems[position]

        // intent to open detail activity and pass data
        val intent = Intent(requiredContext,DetailsActivity::class.java).apply {
            putExtra("MenuItemName",menuItem.foodName)
            putExtra("MenuItemImage",menuItem.foodImage)
            putExtra("MenuItemDescription",menuItem.foodDescription)
            putExtra("MenuItemIngredients",menuItem.foodIngredient)
            putExtra("MenuItemPrice",menuItem.foodPrice)
        }
        //start the detail activity
        requiredContext.startActivity(intent)
    }

    interface OnClickListener{
        fun onItemCLick(position: Int)
    }

}





//class MenuAdapter(private var menuItemsName: MutableList<String>, private var menuItemPrice: MutableList<String>, private var MenuImage: MutableList<Int>, private val requireContext: Context): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
//
//    private val itemClickListener: OnClickListener ?= null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
//        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MenuViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    override fun getItemCount(): Int = menuItemsName.size
//
//    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
//        init {
//            binding.root.setOnClickListener {
//                val position = adapterPosition
//                if(position != RecyclerView.NO_POSITION) {
//                    itemClickListener?.onItemClick(position)
//                }
//                val intent = Intent(requireContext, DetailsActivity::class.java)
//                intent.putExtra("MenuItemName", menuItemsName[position])
//                intent.putExtra("MenuItemImage", MenuImage[position])
//                requireContext.startActivity(intent)
//            }
//        }
//
//        fun bind(position: Int) {
//            binding.apply {
//                menuFoodName.text = menuItemsName[position]
//                menuPrice.text = menuItemPrice[position]
//                menuImage.setImageResource(MenuImage[position])
//            }
//        }
//    }
//    interface OnClickListener {
//        fun onItemClick(position: Int)
//    }
//}