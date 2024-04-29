package com.sanyamj138.mealman

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sanyamj138.mealman.databinding.ActivityDetailsBinding
import com.sanyamj138.mealman.model.CartItems

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodDescription: String? = null
    private var foodImage: String? = null
    private var foodIngredient: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        auth= FirebaseAuth.getInstance()

        foodName = intent.getStringExtra("MenuItemName")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredient = intent.getStringExtra("MenuItemIngredients")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding){
            detailFoodName.text = foodName
            DescriptionTextView.text = foodDescription
            ingredientsText.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(DetailFoodImage)

        }

        binding.buttonBack.setOnClickListener {
            finish()
        }
        binding.addToCartDetail.setOnClickListener {
            addItemToCart()
        }

    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        //create a cart Items object
        val cartItem = CartItems(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodImage.toString(),1)

        //save data to cart item to firebase
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Items Added to cart Successfully 😁", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Items Not Added😒", Toast.LENGTH_SHORT).show()
        }
    }
}

//class DetailsActivity : AppCompatActivity() {
//
//    private lateinit var binding : ActivityDetailsBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_details)
//        binding = ActivityDetailsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val foodName = intent.getStringExtra("MenuItemName")
//        val foodImage = intent.getIntExtra("MenuItemImage", 0)
//        binding.detailFoodName.text = foodName
//        binding.DetailFoodImage.setImageResource(foodImage)
//
//        binding.buttonBack.setOnClickListener {
//            finish()
//        }
//    }
//}