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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.inventory.databinding.ItemLayoutItemBinding

class ItemAdapter(val itemListeners: ItemClickListeners):RecyclerView.Adapter<ItemAdapter.Holder>(){

    private var isUserSelecting = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutItemBinding.inflate(inflater, parent, false)
        return Holder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.counterTextView.text = (1+ position).toString()
    }

    override fun getItemCount(): Int {
        return 96
    }

    private fun refresh(){
        notifyDataSetChanged()
    }

    fun hideCheckBoxes(){
        isUserSelecting = false
        refresh()
    }

    inner class Holder(itemView:View, val binding:ItemLayoutItemBinding)
        :RecyclerView.ViewHolder(itemView){
        init {
            //Show check box
            binding.root.setOnLongClickListener {
                isUserSelecting = !isUserSelecting
                if (isUserSelecting) showCheckBox()
                else hideCheckBox()
                itemListeners.onShowContextMenu(isUserSelecting)
                refresh()
                true
            }
            binding.root.setOnClickListener {
                if (isUserSelecting){
                    val value = binding.checkBox.isChecked
                    binding.checkBox.isChecked = value
                }else{
                    //normal on click
                }
            }
            binding.editButton.setOnClickListener {

            }
            binding.deleteButton.setOnClickListener {

            }
            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                //add item to selected
            }
        }

        private fun showCheckBox(){
            binding.editButton.isEnabled = false
            binding.deleteButton.isEnabled = false
            binding.checkBox.visibility = View.VISIBLE
        }

        private fun hideCheckBox(){
            binding.editButton.isEnabled = true
            binding.deleteButton.isEnabled = true
            binding.checkBox.visibility = View.GONE
        }
    }
}