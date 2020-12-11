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

import android.os.Handler
import androidx.lifecycle.*
import com.lebogang.inventory.LocalRoom.ApplicationRepository
import com.lebogang.inventory.LocalRoom.Models.User
import com.lebogang.inventory.Utils.UserThreadCallbacks
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

class UserViewModel(private val repository: ApplicationRepository):ViewModel() {

    var callback:UserThreadCallbacks? = null

    fun getUserList():LiveData<List<User>> =
        repository.getUserList().asLiveData()

    fun getUserByName(name:String):LiveData<List<User>> =
        repository.getUserByName(name).asLiveData()

    fun getUserBySurname(surname:String):LiveData<List<User>> =
        repository.getUserBySurname(surname).asLiveData()

    fun getUserByNameAndSurname(name: String, surname: String):LiveData<List<User>> =
        repository.getUserByNameAndSurname(name, surname).asLiveData()

    fun getUserByEmail(email: String):LiveData<List<User>> =
        repository.getUserByEmail(email).asLiveData()

    fun getUserTypes(isAdmin:Boolean):LiveData<List<User>> =
        repository.getUserTypes(isAdmin).asLiveData()

    fun checkIfUserExists(email: String, password:String) {
        viewModelScope.launch {
            val result = repository.checkIfUserExists(email, password)
            callback?.onUserExists(result, email, password)
        }
    }

    fun checkIfUserExists(name:String, surname: String, email:String, password:String){
        viewModelScope.launch {
            val result = repository.checkIfUserExists(name, surname, email, password)
            callback?.onUserExists(result, name, surname, email, password)
        }
    }

    fun insertUser(user:User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }

    fun deleteUser(user:User) = viewModelScope.launch {
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