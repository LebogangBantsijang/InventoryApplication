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

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import com.lebogang.inventory.InventoryApplication
import com.lebogang.inventory.LocalRoom.Models.User
import com.lebogang.inventory.Utils.EditTextUtil
import com.lebogang.inventory.Utils.UserThreadCallbacks
import com.lebogang.inventory.ViewModels.UserViewModel
import com.lebogang.inventory.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    private val binding:ActivityCreateAccountBinding by lazy{
        ActivityCreateAccountBinding.inflate(layoutInflater)
    }
    private val viewModel:UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory((application as InventoryApplication).localRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        viewModel.callback = getCallbacks()
    }

    private fun createAccountViews(){
        if (!EditTextUtil.isEditableNull(binding.firstNameEditText.text, binding.lastNameEditText.text,
                binding.emailEditText.text, binding.passwordEditText.text,
                binding.confirmPasswordEditText.text)){
            if (EditTextUtil.doPasswordMatch(binding.passwordEditText.text, binding.confirmPasswordEditText.text)){
                val name = binding.firstNameEditText.text.toString()
                val surname = binding.lastNameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.checkIfUserExists(name, surname, email, password)
            }else
                binding.errorTextView.text =
                    EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.UNCONFIRMED_PASSWORD)
        }else
            binding.errorTextView.text =
                EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NULL_VALUES)
    }

    private fun createUserInLocalDatabase(user: User){
        viewModel.insertUser(user)
        startActivity(Intent(this, ManageUsersActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

    private fun initViews(){
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.createButton.setOnClickListener {
            createAccountViews()
        }
    }

    private fun getCallbacks():UserThreadCallbacks{
        return object : UserThreadCallbacks(){
            override fun onUserExists(result: Boolean, name: String, surname: String, email: String, password: String) {
                if(!result){
                    val user = User(0, 0, name, surname, email, password, false)
                    createUserInLocalDatabase(user)
                }else
                    binding.errorTextView.text =
                            EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.USER_EXISTS)
            }

            override fun onUserExists(result: Boolean, email: String, password: String) {

            }
        }
    }

}