package com.sanyamj138.mealman.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.mealman.adaptar.MenuAdapter
import com.sanyamj138.mealman.databinding.FragmentSearchBinding
import com.sanyamj138.mealman.model.MenuItem

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems = mutableListOf<MenuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //retrieve menu item from database
        retrieveMenuItem()

        //SETUP FOR SEARCHVIEW
        setupSearchView()

        return binding.root
    }

    private fun retrieveMenuItem() {
        //get database reference
        database = FirebaseDatabase.getInstance()
        // referance to the menu node
        val foodReference: DatabaseReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {

                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuItem, requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true // Add return statement here
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true // Add return statement here
            }
        })
    }


    private fun filterMenuItems(query: String) {
        val filteredMenuItems = originalMenuItems.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems)
        adapter.notifyDataSetChanged()
    }

    companion object {

    }
}

//class SearchFragment : Fragment() {
//
//    private lateinit var binding: FragmentSearchBinding
//    private lateinit var adapter : MenuAdapter
//
//    private val originalMenuFoodName = listOf("Pickle", "Khakara", "Thepla", "Pickle", "Khakara", "Thepla")
//    private val originalMenuItemPrice = listOf("296", "239", "152", "296", "239", "152")
//    private val originalMenuImage = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla, R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//       }
//
//    private val filteredMenuFoodName = mutableListOf<String>()
//    private val filteredMenuItemPrice = mutableListOf<String>()
//    private val filteredMenuImage = mutableListOf<Int>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentSearchBinding.inflate(inflater, container, false)
//        adapter = MenuAdapter(filteredMenuFoodName, filteredMenuItemPrice, filteredMenuImage, requireContext())
//        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.menuRecyclerView.adapter = adapter
//
//        // Setup for search view
//
//        setupSearchView()
//
//        // Show all menu items
//
//        showAllMenu()
//
//        return binding.root
//    }
//
//    private fun showAllMenu() {
//        filteredMenuFoodName.clear()
//        filteredMenuItemPrice.clear()
//        filteredMenuImage.clear()
//
//        filteredMenuFoodName.addAll(originalMenuFoodName)
//        filteredMenuItemPrice.addAll(originalMenuItemPrice)
//        filteredMenuImage.addAll(originalMenuImage)
//
//        adapter.notifyDataSetChanged()
//    }
//
//    private fun setupSearchView() {
//        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
//            android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                filterMenuItems(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                filterMenuItems(newText)
//                return true
//            }
//        })
//    }
//
//    private fun filterMenuItems(query: String) {
//        filteredMenuFoodName.clear()
//        filteredMenuItemPrice.clear()
//        filteredMenuImage.clear()
//
//        originalMenuFoodName.forEachIndexed { index, foodName ->
//            if(foodName.contains(query.toString(), ignoreCase = true)) {
//                filteredMenuFoodName.add(foodName)
//                filteredMenuItemPrice.add(originalMenuItemPrice[index])
//                filteredMenuImage.add(originalMenuImage[index])
//            }
//
//            adapter.notifyDataSetChanged()
//        }
//    }
//
//    companion object {
//
//    }
//}
