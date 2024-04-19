package com.sanyamj138.admin_meal_man

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sanyamj138.admin_meal_man.databinding.ActivityLoginBinding
import com.sanyamj138.admin_meal_man.model.UserModel

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private var userName: String? = null
    private lateinit var database: DatabaseReference

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initializing Firebase Auth

        auth = Firebase.auth

        // initialize Firebase database

        database = Firebase.database.reference

        binding.loginButton.setOnClickListener {

            // Getting Data from Edit Text View
            email=binding.signupEmail.text.toString().trim()
            password=binding.signupPassword.text.toString().trim()

            if(email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            }
            else {
                createUserAccount(email, password)
            }
        }

        binding.dontHaveAccountButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
                    if(task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        saveUserData()
                        updateUi(user)
                    } else {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "createUserAccount: Authentication failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {

        // Getting Data from Edit Text View
        email=binding.signupEmail.text.toString().trim()
        password=binding.signupPassword.text.toString().trim()

        val user = UserModel(userName, email, password)
        val userId: String? = FirebaseAuth.getInstance().currentUser?.uid
        userId.let {
            it?.let { it1 -> database.child("user").child(it1).setValue(user) }
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}