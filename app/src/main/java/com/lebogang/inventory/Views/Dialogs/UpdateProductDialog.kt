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
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lebogang.inventory.LocalRoom.Models.Product
import com.lebogang.inventory.R
import com.lebogang.inventory.Utils.EditTextUtil
import com.lebogang.inventory.ViewModels.ProductViewModel
import com.lebogang.inventory.databinding.DialogEditProductBinding

class UpdateProductDialog(private val context:Context,private val viewModel:ProductViewModel) {
    private val dialog = MaterialAlertDialogBuilder(context)
    private val inflater = LayoutInflater.from(context)!!
    private val binding:DialogEditProductBinding by lazy{
        DialogEditProductBinding.inflate(inflater)
    }

    fun show(product: Product){
        dialog.setView(binding.root)
        dialog.setNegativeButton("Cancel", null)
        dialog.setPositiveButton("Update") { dialog, which ->
            if(!EditTextUtil.isEditableNull(binding.nameEditText.text,
                    binding.descriptionEditText.text, binding.priceEditText.text
                    , binding.locationEditText.text, binding.barcodeEditText.text)){
                val name = binding.nameEditText.text.toString()
                val description = binding.descriptionEditText.text.toString()
                val price:Double = binding.priceEditText.text.toString().toDouble()
                val location = binding.locationEditText.text.toString()
                val barcode = binding.barcodeEditText.text.toString()
                val url = binding.imageUrlEditText.text.toString()
                val type = binding.spinner.selectedItem.toString()
                val newProduct = Product(product.productID, barcode,name, description, price, type, location, url)
                viewModel.updateProduct(newProduct)
            }else{
                binding.errorTextView.text = EditTextUtil.getErrorMessage(EditTextUtil.ErrorTypes.NULL_VALUES)
            }
        }
        setProductDetails(product)
        dialog.create().show()
    }

    private fun setProductDetails(product: Product) {
        binding.nameEditText.setText(product.productName)
        binding.descriptionEditText.setText(product.productDescription)
        binding.priceEditText.setText(""+product.productPrice)
        binding.locationEditText.setText(product.availableAtLocation)
        binding.barcodeEditText.setText(product.customProductId)
        binding.imageUrlEditText.setText(product.productImageUrl)
        val array = context.resources.getStringArray(R.array.productTypes)
        var selectionIndex = 0
        for (x in array.indices){
            if (product.productType == array[x]){
                selectionIndex = x
                break
            }
        }
        binding.spinner.setSelection(selectionIndex)
    }
}