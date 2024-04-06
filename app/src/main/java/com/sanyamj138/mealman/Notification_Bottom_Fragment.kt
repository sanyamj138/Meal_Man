package com.sanyamj138.mealman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sanyamj138.mealman.adaptar.NotificationAdapter
import com.sanyamj138.mealman.databinding.FragmentNotificationBottomBinding

class Notification_Bottom_Fragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationBottomBinding.inflate(layoutInflater, container, false)
        val notifications = listOf("Your order has been Canceled Successfully", "Order has been taken by the driver", "Congrats Your Order Placed")
        val notificationImages = listOf(R.drawable.sad, R.drawable.truck, R.drawable.tick)

        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImages)
        )

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}