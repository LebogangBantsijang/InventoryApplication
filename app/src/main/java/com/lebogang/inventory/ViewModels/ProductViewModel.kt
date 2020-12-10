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
import com.lebogang.inventory.LocalRoom.Models.ProductModel
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ApplicationRepository):ViewModel() {

    fun getProductList() : LiveData<List<ProductModel>> =
        repository.getProductList().asLiveData()

    fun getProductsByName(name:String):LiveData<List<ProductModel>> =
        repository.getProductsByName(name).asLiveData()

    fun getProductsByCustomID(customProductId:String):LiveData<List<ProductModel>> =
        repository.getProductsByCustomID(customProductId).asLiveData()

    fun getProductsByPrice(price:Double):LiveData<List<ProductModel>> =
        repository.getProductsByPrice(price).asLiveData()

    fun getProductsInPriceRange(minPrice:Double, maxPrice:Double):LiveData<List<ProductModel>> =
        repository.getProductsInPriceRange(minPrice, maxPrice).asLiveData()

    fun getProductsByType(type:String):LiveData<List<ProductModel>> =
        repository.getProductsByType(type).asLiveData()

    fun getProductInLocation(location:String):LiveData<List<ProductModel>> =
        repository.getProductInLocation(location).asLiveData()


    fun insertProduct(product: ProductModel) = viewModelScope.launch {
        repository.insertProduct(product)
    }

    fun updateProduct(product: ProductModel) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun deleteProduct(product: ProductModel) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun deleteAllProducts() = viewModelScope.launch {
        repository.deleteAllProducts()
    }

    class ProductViewModelFactory(private val repository: ApplicationRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java))
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(repository) as T
            throw IllegalArgumentException("Illegal Argument")
        }
    }
}