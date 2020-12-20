package com.lebogang.inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lebogang.inventory.Views.CreateAccountActivity
import com.lebogang.inventory.Views.ManageInventoryActivity
import com.lebogang.inventory.Views.ManageUsersActivity
import com.lebogang.inventory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    companion object val IS_USER_SIGNED_IN = "user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        hideViews()
        initViews()
    }

    private fun hideViews(){
        if (isUserSignedIn()){
            binding.inventoryButton.visibility = View.VISIBLE
            binding.userButton.visibility = View.VISIBLE
            binding.signButton.visibility = View.GONE
        }else{
            binding.inventoryButton.visibility = View.GONE
            binding.userButton.visibility = View.GONE
            binding.signButton.visibility = View.VISIBLE
        }
    }

    private fun isUserSignedIn():Boolean{
        return getSharedPreferences("Settings", MODE_PRIVATE).getBoolean(IS_USER_SIGNED_IN, false)
    }

    private fun initViews(){
        binding.signButton.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
        binding.userButton.setOnClickListener {
            startActivity(Intent(this, ManageUsersActivity::class.java))
        }
        binding.inventoryButton.setOnClickListener {
            startActivity(Intent(this, ManageInventoryActivity::class.java))
        }
    }

}