package com.sanyamj138.mealman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.mealman.adaptar.MenuAdapter
import com.sanyamj138.mealman.databinding.FragmentMenuBottomSheetBinding
import com.sanyamj138.mealman.model.MenuItem


class MenuBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        binding.buttonBack.setOnClickListener{
            dismiss()
        }

        retrieveMenuItem()

        return binding.root

    }

    private fun retrieveMenuItem() {

        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //loop for through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })

    }

    private fun setAdapter() {
        val adapter = MenuAdapter(menuItems,requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }

    companion object {

    }
}

//class MenuBottomSheetFragment : BottomSheetDialogFragment() {
//
//    private lateinit var binding: FragmentMenuBottomSheetBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)
//
//        binding.buttonBack.setOnClickListener {
//            dismiss()
//        }
//
//        val menuFoodName = listOf("Pickle", "Khakara", "Thepla", "Pickle", "Khakara", "Thepla")
//        val menuItemPrice = listOf("296", "239", "152", "296", "239", "152")
//        val menuImage = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla, R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
//        val adapter = MenuAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice), ArrayList(menuImage), requireContext())
//
//        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.menuRecyclerView.adapter = adapter
//
//        return binding.root
//    }
//
//    companion object {
//
//    }
//}