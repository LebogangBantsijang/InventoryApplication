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

package com.lebogang.inventory.LocalRoom.DAOs

import androidx.room.*
import com.lebogang.inventory.LocalRoom.Models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUserList(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE name = :name")
    fun getUserByName(name:String):Flow<List<User>>

    @Query("SELECT * FROM users WHERE surname = :surname")
    fun getUserBySurname(surname:String):Flow<List<User>>

    @Query("SELECT * FROM users WHERE name = :name AND surname = :surname")
    fun getUserByNameAndSurname(name: String, surname: String):Flow<List<User>>

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String):Flow<List<User>>

    @Query("SELECT * FROM users WHERE isAdmin = :isAdmin")
    fun getUserTypes(isAdmin:Boolean):Flow<List<User>>

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun checkIfUserExists(email:String, password:String):User?

    @Query("SELECT * FROM users WHERE name = :name AND surname = :surname AND email = :email AND password = :password")
    suspend fun checkIfUserExists(name:String, surname: String,email:String, password:String):User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user:User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}