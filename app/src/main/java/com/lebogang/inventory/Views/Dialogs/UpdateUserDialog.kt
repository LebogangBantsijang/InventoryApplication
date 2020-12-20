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

package com.lebogang.inventory.Views.Dialogs

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.inventory.LocalRoom.Models.User
import com.lebogang.inventory.Utils.EditTextUtil
import com.lebogang.inventory.ViewModels.UserViewModel
import com.lebogang.inventory.databinding.DialogEditAccountBinding
import java.util.zip.Inflater

class UpdateUserDialog(val context: Context, private val viewModel:UserViewModel) {
    private val dialog = MaterialAlertDialogBuilder(context)
    private val inflater = LayoutInflater.from(context)!!
    private val binding:DialogEditAccountBinding by lazy {
        DialogEditAccountBinding.inflate(inflater)
    }
    fun show(user:User){
        dialog.setView(binding.root)
        dialog.setNegativeButton("Cancel", null)
        dialog.setPositiveButton("Update") { dialog, which ->
            if(!EditTextUtil.isEditableNull(binding.emailEditText.text,
                            binding.firstNameEditText.text, binding.lastNameEditText.text
                            , binding.passwordEditText.text)){
                val name = binding.firstNameEditText.text.toString()
                val surname = binding.lastNameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                val isAdmin = binding.adminCheckBox.isChecked
                val userUpdate = User(user.userID, 0,name, surname, email, password, isAdmin )
                viewModel.updateUser(userUpdate)
            }else{
                binding.errorTextView.text = EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NULL_VALUES)
            }
        }
        setUserDetails(user)
        dialog.create().show()
    }

    private fun setUserDetails(user:User){
        binding.firstNameEditText.setText(user.name)
        binding.lastNameEditText.setText(user.surname)
        binding.emailEditText.setText(user.email)
        binding.passwordEditText.setText(user.password)
        binding.adminCheckBox.isChecked = user.isAdmin
    }
}