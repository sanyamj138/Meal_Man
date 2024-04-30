package com.sanyamj138.admin_meal_man.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sanyamj138.admin_meal_man.databinding.ActivityAllItemBinding
import com.sanyamj138.admin_meal_man.databinding.DeliveryItemBinding
import com.sanyamj138.admin_meal_man.databinding.ItemItemBinding

class DeliveryAdapter(private val customerName: MutableList<String>,
                      private val statusMoney: MutableList<Boolean>)
    :RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

//                customerName.text = customerName[position]

                if (statusMoney [position]){
                    paymentStatusText.text = "Received"
                }else{
                    paymentStatusText.text = "NotReceived"
                }
                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED
                )
                paymentStatusText.setTextColor(colorMap[statusMoney[position]]?:Color.BLACK)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[statusMoney[position]]?:Color.BLACK)
            }
        }

    }
}


//class DeliveryAdapter(private val customerNames: ArrayList<String>, private val moneyStatus: ArrayList<String>): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
//        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return DeliveryViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    override fun getItemCount(): Int = customerNames.size
//
//    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(position: Int) {
//            binding.apply {
//                customerName.text = customerNames[position]
//                paymentStatusText.text = moneyStatus[position]
//                val colorMap = mapOf(
//                    "Received" to Color.GREEN, "Not Received" to Color.RED, "Pending" to Color.GRAY
//                )
//
//                paymentStatusText.setTextColor(colorMap[moneyStatus[position]]?:Color.BLACK)
//                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[moneyStatus[position]]?: Color.BLACK)
//            }
//        }
//
//    }
//}