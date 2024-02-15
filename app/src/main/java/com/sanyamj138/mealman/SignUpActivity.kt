package com.sanyamj138.mealman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanyamj138.mealman.databinding.ActivityLoginBinding
import com.sanyamj138.mealman.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            intent = Intent(this, ChoosingLocationActivity::class.java)
            startActivity(intent)
        }

        binding.registerTxt.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}