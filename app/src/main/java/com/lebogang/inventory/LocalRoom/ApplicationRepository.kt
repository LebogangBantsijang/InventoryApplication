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
import androidx.lifecycle.asLiveData
import com.lebogang.inventory.LocalRoom.DAOs.ProductDao
import com.lebogang.inventory.LocalRoom.Models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

class ApplicationRepository(private val productDao: ProductDao) {
    /**Product**/
    fun getProductList(): Flow<List<Product>>{
        return productDao.getProductList()
    }

    fun getProductsByName(productName:String): Flow<List<Product>>{
        return productDao.getProductsByName(productName)
    }

    fun getProductsByCustomID(customProductId:String): Flow<List<Product>>{
        return productDao.getProductsByCustomID(customProductId)
    }

    fun getProductsByPrice(price:Double): Flow<List<Product>>{
        return productDao.getProductsByPrice(price)
    }

    fun getProductsInPriceRange(minPrice:Double, maxPrice:Double): Flow<List<Product>>{
        return productDao.getProductsInPriceRange(minPrice, maxPrice)
    }

    fun getProductsByType(type:String): Flow<List<Product>>{
        return productDao.getProductsByType(type)
    }

    fun getProductInLocation(location:String): Flow<List<Product>>{
        return productDao.getProductInLocation(location)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertProduct(product: Product){
        productDao.insertProduct(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateProduct(product: Product){
        productDao.updateProduct(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteProduct(product: Product){
        productDao.deleteProduct(product)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllProducts(){
        productDao.deleteAllProducts()
    }
    /**Product**/

}