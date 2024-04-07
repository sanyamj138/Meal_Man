package com.sanyamj138.mealman

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sanyamj138.mealman.adaptar.CartAdapter
import com.sanyamj138.mealman.adaptar.MenuAdapter
import com.sanyamj138.mealman.databinding.FragmentMenuBottomSheetBinding

class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        val menuFoodName = listOf("Pickle", "Khakara", "Thepla", "Pickle", "Khakara", "Thepla")
        val menuItemPrice = listOf("296", "239", "152", "296", "239", "152")
        val menuImage = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla, R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
        val adapter = MenuAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice), ArrayList(menuImage), requireContext())

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}