/*
 * Copyright (c) 2020. - Lebogang Bantsijang
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * compliance License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License on an "IS BASIS", WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the for specific language governing permissions and limitations
 * under the License.
 */

package com.lebogang.inventory.Views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.lebogang.inventory.InventoryApplication
import com.lebogang.inventory.LocalRoom.Models.User
import com.lebogang.inventory.MainActivity
import com.lebogang.inventory.Utils.EditTextUtil
import com.lebogang.inventory.Utils.UserThreadCallbacks
import com.lebogang.inventory.ViewModels.UserViewModel
import com.lebogang.inventory.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding:ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel:UserViewModel by viewModels{
        UserViewModel.UserViewModelFactory((application as InventoryApplication).localRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initLoginViews()
        viewModel.callback = getCallback()
    }

    private fun initToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private fun initLoginViews(){
        binding.loginButton.setOnClickListener {
            if (!EditTextUtil.isEditableNull(binding.emailEditText.text, binding.passwordEditText.text)){
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.checkIfUserExists(email, password)
            }else
                binding.errorTextView.text = EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NULL_VALUES)
        }
    }

    private fun gotoNextActivity(){
        getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
            .putBoolean("user", true)
            .apply()
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_TASK_ON_HOME
        })
    }

    private fun getCallback():UserThreadCallbacks{
        return object :UserThreadCallbacks(){
            override fun onUserExists(result: Boolean, name: String, surname: String, email: String, password: String) {
            }
            override fun onUserExists(result: Boolean, email: String, password: String) {
                if (result){
                    gotoNextActivity()
                }else
                    binding.errorTextView.text = EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NO_SUCH_USER)
            }

        }
    }

}