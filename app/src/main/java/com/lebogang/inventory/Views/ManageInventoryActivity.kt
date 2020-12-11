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
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebogang.inventory.databinding.ActivityManageInventoryBinding

class ManageInventoryActivity : AppCompatActivity(){
    private val binding:ActivityManageInventoryBinding by lazy{
        ActivityManageInventoryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }


    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initAddButton(){
        binding.addFloatingActionButton.setOnClickListener {

        }
    }

    private fun initCustomContextMenu(){
        binding.closeButton.setOnClickListener {
            binding.contextBar.visibility = View.GONE
        }
        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            //select all
        }
    }

}