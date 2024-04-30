package com.sanyamj138.admin_meal_man

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.admin_meal_man.adapter.PendingOrderAdapter
import com.sanyamj138.admin_meal_man.databinding.ActivityPendingOrderBinding
import com.sanyamj138.admin_meal_man.model.OrderDetails

class PendingOrdersActivity : AppCompatActivity(), PendingOrderAdapter.OnItemCLicked {
    private lateinit var binding: ActivityPendingOrderBinding
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init of database
        database = FirebaseDatabase.getInstance()
        //init of database reference
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrdersDetails()

        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun getOrdersDetails() {
        //retrieve order details from firebase database
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addSataListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun addSataListForRecyclerView() {
        for (orderItem in listOfOrderItem) {
            //add data to resp. list for populating the Recycler View
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =
            PendingOrderAdapter(this,
                listOfName as ArrayList<String>, listOfTotalPrice, listOfImageFirstFoodOrder, this)
        binding.pendingOrdersRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails", userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        // handle item acceptance and update database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderReference = childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }

        clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updatedOrderAcceptStatus(position)

    }

    override fun onItemDispatchClickListener(position: Int) {
        // handle item Dispatch and update database
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference = database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderReference?.setValue(listOfOrderItem[position])
            ?.addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchItemPushKey)
            }

    }

    private fun deleteThisItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemsReference = database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemsReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Order Is Dispatched", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Order Is Not Dispatched", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updatedOrderAcceptStatus(position: Int) {
        // update order acceptance in User's BuyHistory and OrderDetails
        val userIdOfCLickedItem = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryReference =
            database.reference.child("user").child(userIdOfCLickedItem!!).child("BuyHistory")
                .child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("orderAccepted").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)
    }
}


//class PendingOrderActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityPendingOrderBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.backButton.setOnClickListener {
//            finish()
//        }
//
//        val orderedCustomName = arrayListOf("Mayank Badaulia", "Sanyam Jain", "V Soumya")
//        val orderedQuantity = arrayListOf("8", "5", "6")
//        val orderedFoodImage = arrayListOf(R.drawable.kakhra, R.drawable.thepla, R.drawable.pickle)
//        val adapter = PendingOrderAdapter(orderedCustomName, orderedQuantity, orderedFoodImage, this)
//        binding.pendingOrdersRecyclerView.adapter = adapter
//        binding.pendingOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
//    }
//}

