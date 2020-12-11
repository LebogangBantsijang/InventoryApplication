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
import com.lebogang.inventory.LocalRoom.Models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao{
    @Query("SELECT * FROM products")
    fun getProductList(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productName = :productName")
    fun getProductsByName(productName:String):Flow<List<Product>>

    @Query("SELECT * FROM products WHERE customProductId = :customProductId")
    fun getProductsByCustomID(customProductId:String):Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productPrice = :price")
    fun getProductsByPrice(price:Double):Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productPrice >= :minPrice AND productPrice <= :maxPrice")
    fun getProductsInPriceRange(minPrice:Double, maxPrice:Double):Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productType = :type")
    fun getProductsByType(type:String):Flow<List<Product>>

    @Query("SELECT * FROM products WHERE availableAtLocation = :location")
    fun getProductInLocation(location:String):Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product:Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
}