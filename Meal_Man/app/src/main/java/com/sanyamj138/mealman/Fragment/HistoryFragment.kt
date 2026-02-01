package com.sanyamj138.mealman.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.mealman.R
import com.sanyamj138.mealman.RecentOrderItemsActivity
import com.sanyamj138.mealman.adaptar.BuyAgainAdapter
import com.sanyamj138.mealman.databinding.FragmentHistoryBinding
import com.sanyamj138.mealman.model.OrderDetails

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        //retrieve and display the user order history
        retrieveBuyHistory()

        //recent buy Button Click
        binding.cardViewRecentBuy.setOnClickListener {
            seeItemsRecentBuy()
        }

        binding.recievedBtn.setOnClickListener{
            updateOrderStatus()
        }

        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    //function to see items recent buy
    private fun seeItemsRecentBuy() {
        listOfOrderItem.firstOrNull()?.let { recentBuy ->
            val arrayListOfOrderItem = ArrayList(listOfOrderItem)
            val intent = Intent(requireContext(), RecentOrderItemsActivity::class.java)
            intent.putExtra("RecentBuyOrderItem", arrayListOfOrderItem)
            startActivity(intent)
        }
    }

    //function to retrieve items buy history
    private fun retrieveBuyHistory() {
        binding.cardViewRecentBuy.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""

        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    //display the most recent order details
                    setDataInRecentBuyItem()
                    //setup the recyclerview with previous order details
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //function to most recent order details
    private fun setDataInRecentBuyItem() {
        binding.cardViewRecentBuy.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                historyFoodName.text = it.foodNames?.firstOrNull() ?: ""
                historyItemPrice.text = it.foodPrices?.firstOrNull() ?: ""
                val image = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(historyImage)

                val isOrderIsAccepted = listOfOrderItem[0].orderAccepted
                Log.d("TAG", "setDataInRecentBuyItem: $isOrderIsAccepted")
                if (isOrderIsAccepted){
                    orderStatus.background.setTint(Color.GREEN)
                    recievedBtn.visibility = View.VISIBLE
                }

                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {

                }

            }
        }
    }

    //function to setup the recycler view
    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { buyAgainFoodName.add(it) }
            listOfOrderItem[i].foodPrices?.firstOrNull()?.let { buyAgainFoodPrice.add(it) }
            listOfOrderItem[i].foodImages?.firstOrNull()?.let { buyAgainFoodImage.add(it) }
        }

        val rv = binding.BuyAgainRecyclerView
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter = BuyAgainAdapter(
            buyAgainFoodName,
            buyAgainFoodPrice,
            buyAgainFoodImage,
            requireContext()
        )
        rv.adapter = buyAgainAdapter
    }

}

//class HistoryFragment : Fragment() {
//
//    private lateinit var binding: FragmentHistoryBinding
//    private  lateinit var buyAgainAdapter: BuyAgainAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
//        setupRecyclerView()
//        return binding.root
//    }
//
//    private fun setupRecyclerView() {
//        val buyAgainFoodName = arrayListOf("Pickle", "Khakara", "Thepla")
//        val buyAgainFoodPrice = arrayListOf("296", "239", "152")
//        val buyAgainFoodImage = arrayListOf(R.drawable.pickle, R.drawable.kakhra, R.drawable.thepla)
//        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage)
//        binding.BuyAgainRecyclerView.adapter = buyAgainAdapter
//        binding.BuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//    }
//
//    companion object {
//    }
//}