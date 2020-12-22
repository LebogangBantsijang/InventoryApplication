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

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lebogang.inventory.InventoryApplication
import com.lebogang.inventory.LocalRoom.Models.Product
import com.lebogang.inventory.R
import com.lebogang.inventory.Utils.EditTextUtil
import com.lebogang.inventory.ViewModels.ProductViewModel
import com.lebogang.inventory.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private val binding: ActivityAddProductBinding by lazy{
        ActivityAddProductBinding.inflate(layoutInflater)
    }
    private val viewModel:ProductViewModel by viewModels {
        ProductViewModel.ProductViewModelFactory((application as InventoryApplication).localRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initSaveViews()
    }

    private fun initToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initSaveViews(){
        binding.saveButton.setOnClickListener {
            if (!EditTextUtil.isEditableNull(binding.nameEditText.text, binding.descriptionEditText.text
                , binding.priceEditText.text, binding.locationEditText.text, binding.barcodeEditText.text)){
                val name = binding.nameEditText.text.toString()
                val description = binding.descriptionEditText.text.toString()
                val price = binding.priceEditText.text.toString().toDouble()
                val location = binding.locationEditText.text.toString()
                val barcode=  binding.barcodeEditText.text.toString()
                val type = binding.spinner.selectedItem.toString()
                val url = getImageUrl()
                val product = Product(0, barcode, name, description, price, type, location,url)
                viewModel.insertProduct(product)
                onBackPressed()
            }else
                binding.errorTextView.text = EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NULL_VALUES)
        }
    }

    private fun getImageUrl():String{
        if (EditTextUtil.isEditableNull(binding.imageUrlEditText.text)){
            val url = binding.imageUrlEditText.text.toString()
            if (URLUtil.isValidUrl(url))
                return url
        }
        return ""
    }

}