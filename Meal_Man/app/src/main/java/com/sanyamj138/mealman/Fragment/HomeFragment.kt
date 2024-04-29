package com.sanyamj138.mealman.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.mealman.MenuBottomSheetFragment
import com.sanyamj138.mealman.R
import com.sanyamj138.mealman.adaptar.MenuAdapter
import com.sanyamj138.mealman.adaptar.PopularAdapter
import com.sanyamj138.mealman.databinding.FragmentHomeBinding
import com.sanyamj138.mealman.model.MenuItem

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }

        //retrieve and display popular item
        retrieveAndDisplayPopularItems()

        return binding.root
    }

    private fun retrieveAndDisplayPopularItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        //retrieve menu items from database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //loop for through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun randomPopularItems() {
        //create as shuffled list of menu items
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetMenuItems = index.take(numItemToShow).map { menuItems[it] }

        setPopularItemsAdapter(subsetMenuItems)
    }

    private fun setPopularItemsAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItems,requireContext())
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })


    }
}

//class HomeFragment : Fragment() {
//
//    private lateinit var binding: FragmentHomeBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
//
//        binding.viewAllMenu.setOnClickListener {
//            val bottomSheetDialog = MenuBottomSheetFragment()
//            bottomSheetDialog.show(parentFragmentManager, "Test")
//        }
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val imageList = ArrayList<SlideModel>()
//        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
//        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
//        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
//
//        val imageSlider = binding.imageSlider
//        imageSlider.setImageList(imageList)
//        imageSlider.setImageList(imageList, ScaleTypes.FIT)
//        imageSlider.setItemClickListener(object :ItemClickListener{
//            override fun doubleClick(position: Int) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onItemSelected(position: Int) {
//                val itemPosition = imageList[position]
//                val itemMessage = "Selected Image $position"
//                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
//            }
//        })
//        val foodName = listOf("Pickle", "Khakara", "Thepla")
//        val Price = listOf("296", "239", "152")
//        val popularFoodImages = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
//
//        val adapter = PopularAdapter(foodName, Price, popularFoodImages, requireContext())
//        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.PopularRecyclerView.adapter = adapter
//    }
//
//    companion object {
//
//    }
//}