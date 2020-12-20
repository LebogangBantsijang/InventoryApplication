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

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.inventory.InventoryApplication
import com.lebogang.inventory.LocalRoom.Models.User
import com.lebogang.inventory.R
import com.lebogang.inventory.ViewModels.UserViewModel
import com.lebogang.inventory.Views.Dialogs.UpdateUserDialog
import com.lebogang.inventory.Views.Interfaces.UserClickInterface
import com.lebogang.inventory.Views.RecyclerAdapters.ManageUsersAdapter
import com.lebogang.inventory.databinding.ActivityManageUsersBinding

class ManageUsersActivity : AppCompatActivity(), UserClickInterface {
    private val binding:ActivityManageUsersBinding by lazy{
        ActivityManageUsersBinding.inflate(layoutInflater)
    }
    private val viewModel:UserViewModel by viewModels{
        UserViewModel.UserViewModelFactory((application as InventoryApplication).localRepository)
    }
    private val adapter = ManageUsersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initSearchViews()
        initRecyclerView()
        getData()
    }

    private fun initToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manage_user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search ->{
                toggleSearchView(true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearchViews(){
        binding.closeButton.setOnClickListener {
            binding.searchEditText.text!!.clear()
            toggleSearchView(false)
        }
        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            adapter.filter.filter(text)
        }

    }

    private fun toggleSearchView(show:Boolean){
        if (show){
            binding.toolbar.isClickable = false
            binding.searchContainer.visibility = View.VISIBLE

        }else{
            binding.toolbar.isClickable = true
            binding.searchContainer.visibility = View.GONE
        }
    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getData(){
        viewModel.getUserList().observe(this,{
            adapter.setUserData(it)
        })
    }

    override fun onClick(user: User) {
        UpdateUserDialog(this, viewModel).show(user)
    }

    override fun onDeleteClick(user: User) {
        viewModel.deleteUser(user)
    }
}