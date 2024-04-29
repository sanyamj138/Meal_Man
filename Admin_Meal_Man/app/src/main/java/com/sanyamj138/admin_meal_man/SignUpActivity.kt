package com.sanyamj138.admin_meal_man

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sanyamj138.admin_meal_man.databinding.ActivitySignUpBinding
import com.sanyamj138.admin_meal_man.model.UserModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var UserName: String
    private lateinit var Email: String
    private lateinit var Password: String
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(com.sanyamj138.admin_meal_man.R.string.default_web_client_id)).requestEmail().build()

        //Intialization Firebase auth
        auth = Firebase.auth

        //Intialization Firebase Database
        database = com.google.firebase.Firebase.database.reference

        //Google Sign In
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.createUserButton.setOnClickListener {

            //get text from EditText
            UserName = binding.signupName.text.toString().trim()
            Email = binding.signupEmail.text.toString().trim()
            Password = binding.signupPassword.text.toString().trim()

            if (UserName.isBlank() || Email.isBlank() || Password.isBlank() ){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(Email,Password)
            }

        }
        binding.alreadyHaveAnAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.googleButton.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }

        val locationList = arrayOf("Jaipur", "Bhopal", "Kota", "Guna")

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationList)

        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

    }


    // Launcher for google SignIn
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account : GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener { Task ->
                    if (Task.isSuccessful){
                        // successfully sign in with google
                        startActivity (Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Google Sign-in Fail", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Google Sign-in Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure",task.exception)
            }
        }
    }

    //Save data into Database
    private fun saveUserData() {
        //get text from EditText
        UserName = binding.signupName.text.toString().trim()
        Email = binding.signupEmail.text.toString().trim()
        Password = binding.signupPassword.text.toString().trim()

        val user = UserModel(UserName,Email,Password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        //save user data in firebase Database
        database.child("user").child(userId).setValue(user)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var email: String
//    private lateinit var password: String
//    private lateinit var userName: String
//    private lateinit var database: DatabaseReference
//
//    private val binding:ActivitySignUpBinding by lazy {
//        ActivitySignUpBinding.inflate(layoutInflater)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        // Initializing Firebase Auth
//
//        auth = Firebase.auth
//
//        // initialize Firebase database
//
//        database = Firebase.database.reference
//
//
//        val locationList = arrayOf("Jaipur", "Bhopal", "Kota", "Guna")
//        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, locationList)
//        val autoCompleteTextView = binding.listOfLocation
//        autoCompleteTextView.setAdapter(adapter)
//
//        binding.createUserButton.setOnClickListener {
//
//            // Getting Data from Edit Text View
//            email=binding.signupEmail.text.toString().trim()
//            userName=binding.signupName.text.toString().trim()
//            password=binding.signupPassword.text.toString().trim()
//
//            if(userName.isBlank() || email.isBlank() || password.isBlank()) {
//                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                createAccount(email, password)
//            }
//        }
//
//        binding.alreadyHaveAnAccount.setOnClickListener {
//            intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun createAccount(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if(task.isSuccessful) {
//                Toast.makeText(this, "Account Created Successfully!!", Toast.LENGTH_SHORT).show()
//                saveUserData()
//                intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//            else {
//                Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
//                Log.d("Account","createAccount: Failure", task.exception)
//            }
//        }
//    }
//
//    // Save Data into Database
//    private fun saveUserData() {
//        // Getting Data from Edit Text View
//        email=binding.signupEmail.text.toString().trim()
//        userName=binding.signupName.text.toString().trim()
//        password=binding.signupPassword.text.toString().trim()
//
//        val user = UserModel(userName, email, password)
//        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid
//
//        // Save user data Firebase Database
//        database.child( "user").child(userId).setValue(user)
//    }
//}