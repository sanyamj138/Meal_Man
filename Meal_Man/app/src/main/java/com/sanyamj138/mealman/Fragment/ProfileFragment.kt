package com.sanyamj138.mealman.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sanyamj138.mealman.R
import com.sanyamj138.mealman.UserModel
import com.sanyamj138.mealman.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private var auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.profileNameEditText.isEnabled = false
        binding.profileAddressEditText.isEnabled = false
        binding.profileEmailEditText.isEnabled = false
        binding.profilePhoneEditText.isEnabled = false

        var isEnable = false

        binding.profileEditButton.setOnClickListener {
            isEnable = ! isEnable

            binding.profileNameEditText.isEnabled = isEnable
            binding.profileAddressEditText.isEnabled = isEnable
            binding.profileEmailEditText.isEnabled = isEnable
            binding.profilePhoneEditText.isEnabled = isEnable

            if(isEnable){
                binding.profileNameEditText.requestFocus()
            }
        }

        setUserData()

        binding.profileSubmitButton.setOnClickListener{
            val name = binding.profileNameEditText.text.toString()
            val email = binding.profileEmailEditText.text.toString()
            val address = binding.profileAddressEditText.text.toString()
            val phone = binding.profilePhoneEditText.text.toString()

            updateUserData(name,email,address,phone)

            binding.profileNameEditText.isEnabled = false
            binding.profileAddressEditText.isEnabled = false
            binding.profileEmailEditText.isEnabled = false
            binding.profilePhoneEditText.isEnabled = false
        }

        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReference = database.getReference("user").child(userId)

            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone,
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile Update Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Profile Update Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null){
                            binding.profileNameEditText.setText(userProfile.name)
                            binding.profileAddressEditText.setText(userProfile.address)
                            binding.profileEmailEditText.setText(userProfile.email)
                            binding.profilePhoneEditText.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


        }
    }

}

//class ProfileFragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
//    }
//
//    companion object {
//
//    }
//}