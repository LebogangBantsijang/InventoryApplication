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

package com.lebogang.inventory.Views.RecyclerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lebogang.inventory.LocalRoom.Models.Product
import com.lebogang.inventory.R
import com.lebogang.inventory.Views.Interfaces.ProductClickInterface
import com.lebogang.inventory.databinding.ItemLayoutProductBinding
import java.util.*
import kotlin.collections.ArrayList

class ManageProductsAdapter(val productClickInterface: ProductClickInterface)
    :RecyclerView.Adapter<ManageProductsAdapter.Holder>(), Filterable {

    private var list:List<Product> = arrayListOf()
    private val searchList:ArrayList<Product> = arrayListOf()
    private var isUserSearching = false
    private lateinit var contrext: Context

    fun setProductData(products:List<Product>){
        list = products
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View, val  binding:ItemLayoutProductBinding)
        :RecyclerView.ViewHolder(itemView){
        init {
            binding.root.setOnClickListener {
                productClickInterface.onClick(list[adapterPosition])
            }
            binding.deleteButton.setOnClickListener {
                productClickInterface.onDelete(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutProductBinding.inflate(inflater, parent, false)
        return Holder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (isUserSearching){
            val product = searchList[position]
            holder.binding.titleTextView.text = product.productName
            holder.binding.descriptionTextView.text = product.productDescription
            holder.binding.locationTextView.text = product.availableAtLocation
            holder.binding.typeTextView.text = product.productType
            holder.binding.customIdTextView.text = product.customProductId
            holder.binding.priceTextView.text = "R"+ product.productPrice
            Glide.with(holder.itemView)
                    .load(product.productImageUrl)
                    .error(R.drawable.ic_trolley)
                    .into(holder.binding.productImageView)
        }else{
            val product = list[position]
            holder.binding.titleTextView.text = product.productName
            holder.binding.descriptionTextView.text = product.productDescription
            holder.binding.locationTextView.text = product.availableAtLocation
            holder.binding.typeTextView.text = product.productType
            holder.binding.customIdTextView.text = product.customProductId
            holder.binding.priceTextView.text = "R"+ product.productPrice
            Glide.with(holder.itemView)
                    .load(product.productImageUrl)
                    .error(R.drawable.ic_trolley)
                    .into(holder.binding.productImageView)
        }
    }

    override fun getItemCount(): Int {
        if (isUserSearching)
            return searchList.size
        return list.size
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                isUserSearching = !constraint.isNullOrEmpty()
                if (isUserSearching){
                    searchList.clear()
                    list.forEach {
                        if (it.productName.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))){
                            searchList.add(it)
                        }
                    }
                }
                return FilterResults()
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }
}