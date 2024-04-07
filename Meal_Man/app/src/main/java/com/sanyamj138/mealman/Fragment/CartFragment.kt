package com.sanyamj138.mealman.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanyamj138.mealman.CongratsBottomSheet
import com.sanyamj138.mealman.PayOutActivity
import com.sanyamj138.mealman.R
import com.sanyamj138.mealman.adaptar.CartAdapter
import com.sanyamj138.mealman.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartFoodName = listOf("Pickle", "Khakara", "Thepla")
        val cartItemPrice = listOf("296", "239", "152")
        val cartImage = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        binding.proceedButton.setOnClickListener {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}