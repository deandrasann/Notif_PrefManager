package com.example.boardcast

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.boardcast.databinding.ActivityLoginBinding
import com.example.boardcast.databinding.ActivityMainBinding

class LoginActivtiy : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    private var usernameAccount = "Deandra Santoso"
    private var passAccount ="123123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)

        with(binding){
            btnLogin.setOnClickListener {
                val usernameInput = edtUsername.text.toString()
                val passInput = edtPassword.text.toString()

                if (usernameInput == usernameAccount && passInput == passAccount ){
                    prefManager.saveUsername(usernameInput)
                    checkLoginStatus()
                }
                else{
                    Toast.makeText(this@LoginActivtiy, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun checkLoginStatus(){
        val username = prefManager.getUsername()
        if (username.isNotEmpty()){
            startActivity(Intent(this@LoginActivtiy, MainActivity::class.java))
            finish()
        }
    }
}