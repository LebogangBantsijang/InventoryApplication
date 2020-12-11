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
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.lebogang.inventory.LocalRoom.Models.User
import com.lebogang.inventory.Views.Interfaces.UserClickInterface
import com.lebogang.inventory.databinding.ItemLayoutUserBinding
import java.util.*
import kotlin.collections.ArrayList

class ManageUsersAdapter(private val click:UserClickInterface)
    :RecyclerView.Adapter<ManageUsersAdapter.Holder>(), Filterable {

    private var list:List<User> = arrayListOf()
    private val searchList:ArrayList<User> = arrayListOf()
    private var isUserSearching = false

    fun setUserData(users:List<User>){
        list = users
        notifyDataSetChanged()
    }

    inner class Holder(itemView:View, val binding:ItemLayoutUserBinding)
        :RecyclerView.ViewHolder(itemView){
        init {
            binding.root.setOnClickListener {
                click.onClick(list[adapterPosition])
            }
            binding.deleteButton.setOnClickListener {
                click.onDeleteClick(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutUserBinding.inflate(inflater, parent, false)
        return Holder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (isUserSearching){
            val user = searchList[position]
            holder.binding.nameTextView.text = user.name + " " + user.surname
            holder.binding.surnameTextView.text = user.email
            if (user.isAdmin)
                holder.binding.adminTextView.text = "Admin"
            else
                holder.binding.adminTextView.text = ""
        }else{
            val user = list[position]
            holder.binding.nameTextView.text = user.name + " " + user.surname
            holder.binding.surnameTextView.text = user.email
            if (user.isAdmin)
                holder.binding.adminTextView.text = "Admin"
            else
                holder.binding.adminTextView.text = ""
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
                        if (it.name.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))
                                ||it.surname.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))){
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