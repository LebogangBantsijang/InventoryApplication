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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import com.lebogang.inventory.InventoryApplication
import com.lebogang.inventory.LocalRoom.Models.UserModel
import com.lebogang.inventory.Utils.EditTextUtil
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
    }

    private fun createAccount(){
        if (isEditableNull(binding.firstNameEditText.text, binding.lastNameEditText.text,
                binding.usernameEditText.text, binding.passwordEditText.text,
                binding.confirmPasswordEditText.text)){
            if (doPasswordMatch(binding.passwordEditText.text, binding.confirmPasswordEditText.text)){
                val name = binding.firstNameEditText.text.toString()
                val surname = binding.firstNameEditText.text.toString()
                val username = binding.firstNameEditText.text.toString()
                val password = binding.firstNameEditText.text.toString()
                val user = UserModel(0, 0, name, surname, username, password, false)
                createUserInLocalDatabase(user)
            }else
                binding.errorTextView.text =
                    EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.UNCONFIRMED_PASSWORD)
        }else
            binding.errorTextView.text =
                EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NULL_VALUES)
    }

    private fun createUserInLocalDatabase(user: UserModel){
        viewModel.insertUser(user)
    }

    private fun doPasswordMatch(password:Editable?, confirmPassword:Editable?):Boolean{
        if (password.toString() == confirmPassword.toString())
            return true
        return false
    }

    private fun isEditableNull(vararg editable: Editable?):Boolean{
        editable.iterator().forEach {
            if (it.isNullOrEmpty()||it.isNullOrBlank())
                return true
        }
        return false
    }

}