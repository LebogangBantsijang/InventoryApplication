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

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.inventory.InventoryApplication
import com.lebogang.inventory.LocalRoom.Models.Product
import com.lebogang.inventory.R
import com.lebogang.inventory.ViewModels.ProductViewModel
import com.lebogang.inventory.Views.Dialogs.UpdateProductDialog
import com.lebogang.inventory.Views.Interfaces.ProductClickInterface
import com.lebogang.inventory.Views.RecyclerAdapters.ManageProductsAdapter
import com.lebogang.inventory.databinding.ActivityManageInventoryBinding

class ManageInventoryActivity : AppCompatActivity(), ProductClickInterface{
    private val binding:ActivityManageInventoryBinding by lazy{
        ActivityManageInventoryBinding.inflate(layoutInflater)
    }
    private val adapter = ManageProductsAdapter(this)
    private val viewModel:ProductViewModel by viewModels {
        ProductViewModel.ProductViewModelFactory((application as InventoryApplication).localRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initSearchViews()
        initAddView()
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
        menuInflater.inflate(R.menu.manage_inventory_menu, menu)
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

    private fun initAddView(){
        binding.addFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }
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

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getData(){
        viewModel.getProductList().observe(this, {
            adapter.setProductData(it)
        })
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

    override fun onClick(product: Product) {
        UpdateProductDialog(this, viewModel).show(product)
    }

    override fun onDelete(product: Product) {
        viewModel.deleteProduct(product)
    }
}