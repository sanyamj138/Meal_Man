package com.sanyamj138.mealman.adaptar

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanyamj138.mealman.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val buyAgainFoodName:MutableList<String>,
    private val buyAgainFoodPrice:MutableList<String>,
    private val buyAgainFoodImage:MutableList<String>,
    private var requireContext: Context
): RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {

        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodImage[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int  = buyAgainFoodName.size

    inner class BuyAgainViewHolder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(foodName: String, foodPrice: String, foodImage: String) {
            binding.buyAgainFoodName.text = foodName
            binding.buyAgainFoodPrice.text = foodPrice
            val uriString = foodImage
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainFoodImage)
        }

    }


}

//class BuyAgainAdapter(private val buyAgainFoodName: ArrayList<String>, private val buyAgainFoodPrice: ArrayList<String>, private val buyAgainFoodImage: ArrayList<Int>) :
//    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
//        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return BuyAgainViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = buyAgainFoodName.size
//
//    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
//        holder.bind(buyAgainFoodName[position], buyAgainFoodPrice[position], buyAgainFoodImage[position])
//    }
//
//    class BuyAgainViewHolder(private val binding: BuyAgainItemBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(foodName: String, foodPrice: String, foodImage: Int) {
//            binding.buyAgainFoodName.text = foodName
//            binding.buyAgainFoodPrice.text = foodPrice
//            binding.buyAgainFoodImage.setImageResource(foodImage)
//        }
//
//    }
//}