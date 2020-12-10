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

package com.lebogang.inventory.LocalRoom

import androidx.annotation.WorkerThread
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lebogang.inventory.LocalRoom.DAOs.ProductDao
import com.lebogang.inventory.LocalRoom.DAOs.UserDao
import com.lebogang.inventory.LocalRoom.Models.ProductModel
import com.lebogang.inventory.LocalRoom.Models.UserModel
import kotlinx.coroutines.flow.Flow

class ApplicationRepository(private val productDao: ProductDao, private val userDao: UserDao) {
    /**Product**/
    fun getProductList(): Flow<List<ProductModel>>{
        return productDao.getProductList()
    }

    fun getProductsByName(productName:String): Flow<List<ProductModel>>{
        return productDao.getProductsByName(productName)
    }

    fun getProductsByCustomID(customProductId:String): Flow<List<ProductModel>>{
        return productDao.getProductsByCustomID(customProductId)
    }

    fun getProductsByPrice(price:Double): Flow<List<ProductModel>>{
        return productDao.getProductsByPrice(price)
    }

    fun getProductsInPriceRange(minPrice:Double, maxPrice:Double): Flow<List<ProductModel>>{
        return productDao.getProductsInPriceRange(minPrice, maxPrice)
    }

    fun getProductsByType(type:String): Flow<List<ProductModel>>{
        return productDao.getProductsByType(type)
    }

    fun getProductInLocation(location:String): Flow<List<ProductModel>>{
        return productDao.getProductInLocation(location)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertProduct(product: ProductModel){
        productDao.insertProduct(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateProduct(product: ProductModel){
        productDao.updateProduct(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteProduct(product: ProductModel){
        productDao.deleteProduct(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }
    /**Product**/

    /**User**/
    fun getUserList(): Flow<List<UserModel>>{
        return userDao.getUserList()
    }

    fun getUserByName(name:String):Flow<List<UserModel>>{
        return userDao.getUserByName(name)
    }

    fun getUserBySurname(surname:String):Flow<List<UserModel>>{
        return userDao.getUserBySurname(surname)
    }

    fun getUserByNameAndSurname(name: String, surname: String):Flow<List<UserModel>>{
        return userDao.getUserByNameAndSurname(name, surname)
    }

    fun getUserByEmail(email: String):Flow<List<UserModel>>{
        return userDao.getUserByEmail(email)
    }

    fun getUserTypes(isAdmin:Boolean):Flow<List<UserModel>>{
        return userDao.getUserTypes(isAdmin)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user:UserModel){
        userDao.insertUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: UserModel){
        userDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteUser(user:UserModel){
        userDao.deleteUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
    /**User**/
}