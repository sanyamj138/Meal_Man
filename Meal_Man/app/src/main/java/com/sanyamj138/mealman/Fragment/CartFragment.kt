package com.sanyamj138.mealman.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.mealman.CongratsBottomSheet
import com.sanyamj138.mealman.PayOutActivity
import com.sanyamj138.mealman.R
import com.sanyamj138.mealman.adaptar.CartAdapter
import com.sanyamj138.mealman.databinding.FragmentCartBinding
import com.sanyamj138.mealman.model.CartItems

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImagesUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()


        binding.proceedButton.setOnClickListener {
            //get order item details before proceeding to check out
            getOrderItemsDetails()
        }
        return binding.root
    }

    private fun getOrderItemsDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        val foodName = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredients = mutableListOf<String>()
        //get items Quantities
        val foodQuantity = cartAdapter.getUpdatedItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cartITerms to respective list
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    //add items details in to list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngredients?.let { foodIngredients.add(it) }
                }
                orderNow(
                    foodName,
                    foodPrice,
                    foodIngredients,
                    foodDescription,
                    foodImage,
                    foodQuantity
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order making Failed.Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodQuantity: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("FoodItemsName", foodName as ArrayList<String>)
            intent.putExtra("FoodItemsPrice", foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemsIngredients", foodIngredients as ArrayList<String>)
            intent.putExtra("FoodItemsDescription", foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemsImage", foodImage as ArrayList<String>)
            intent.putExtra("FoodItemsQuantity", foodQuantity as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        // database reference to firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        //list to store cart items
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodImagesUri = mutableListOf()
        foodIngredients = mutableListOf()
        quantity = mutableListOf()

        //fetch data form database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cart items object from child node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)

                    //add cart item details to list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngredients?.let { foodIngredients.add(it) }
                }

                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not Fetch", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun setAdapter() {
        cartAdapter = CartAdapter(
            requireContext(),
            foodNames,
            foodPrices,
            foodImagesUri,
            foodDescriptions,
            quantity,
            foodIngredients
        )
        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartRecyclerView.adapter = cartAdapter
    }

    companion object {

    }
}

//class CartFragment : Fragment() {
//
//    private lateinit var binding: FragmentCartBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentCartBinding.inflate(inflater, container, false)
//
//        val cartFoodName = listOf("Pickle", "Khakara", "Thepla")
//        val cartItemPrice = listOf("296", "239", "152")
//        val cartImage = listOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
//        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImage))
//
//        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.cartRecyclerView.adapter = adapter
//
//        binding.proceedButton.setOnClickListener {
//            val intent = Intent(requireContext(), PayOutActivity::class.java)
//            startActivity(intent)
//        }
//
//        return binding.root
//    }
//
//    companion object {
//
//    }
//}