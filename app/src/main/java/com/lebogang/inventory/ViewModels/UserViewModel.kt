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

package com.lebogang.inventory.ViewModels

import androidx.lifecycle.*
import com.lebogang.inventory.LocalRoom.ApplicationRepository
import com.lebogang.inventory.LocalRoom.Models.UserModel
import kotlinx.coroutines.launch

class UserViewModel(private val repository: ApplicationRepository):ViewModel() {

    fun getUserList():LiveData<List<UserModel>> =
        repository.getUserList().asLiveData()

    fun getUserByName(name:String):LiveData<List<UserModel>> =
        repository.getUserByName(name).asLiveData()

    fun getUserBySurname(surname:String):LiveData<List<UserModel>> =
        repository.getUserBySurname(surname).asLiveData()

    fun getUserByNameAndSurname(name: String, surname: String):LiveData<List<UserModel>> =
        repository.getUserByNameAndSurname(name, surname).asLiveData()

    fun getUserByEmail(email: String):LiveData<List<UserModel>> =
        repository.getUserByEmail(email).asLiveData()

    fun getUserTypes(isAdmin:Boolean):LiveData<List<UserModel>> =
        repository.getUserTypes(isAdmin).asLiveData()

    fun insertUser(user:UserModel) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun updateUser(user: UserModel) = viewModelScope.launch {
        repository.updateUser(user)
    }

    fun deleteUser(user:UserModel) = viewModelScope.launch {
        repository.deleteUser(user)
    }

    fun deleteAllUsers() = viewModelScope.launch {
        repository.deleteAllUsers()
    }

    class UserViewModelFactory(private val repository: ApplicationRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java))
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repository) as T
            throw IllegalArgumentException("")
        }

    }
}