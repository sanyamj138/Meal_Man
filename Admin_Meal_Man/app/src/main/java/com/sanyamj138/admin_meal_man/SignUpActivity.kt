package com.sanyamj138.admin_meal_man

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sanyamj138.admin_meal_man.databinding.ActivitySignUpBinding
import com.sanyamj138.admin_meal_man.model.UserModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var database: DatabaseReference

    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initializing Firebase Auth

        auth = Firebase.auth

        // initialize Firebase database

        database = Firebase.database.reference


        val locationList = arrayOf("Jaipur", "Bhopal", "Kota", "Guna")
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

        binding.createUserButton.setOnClickListener {

            // Getting Data from Edit Text View
            email=binding.signupEmail.text.toString().trim()
            userName=binding.signupName.text.toString().trim()
            password=binding.signupPassword.text.toString().trim()

            if(userName.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
            else {
                createAccount(email, password)
            }
        }

        binding.alreadyHaveAnAccount.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully!!", Toast.LENGTH_SHORT).show()
                saveUserData()
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure", task.exception)
            }
        }
    }

    // Save Data into Database
    private fun saveUserData() {
        // Getting Data from Edit Text View
        email=binding.signupEmail.text.toString().trim()
        userName=binding.signupName.text.toString().trim()
        password=binding.signupPassword.text.toString().trim()

        val user = UserModel(userName, email, password)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        // Save user data Firebase Database
        database.child( "user").child(userId).setValue(user)
    }
}