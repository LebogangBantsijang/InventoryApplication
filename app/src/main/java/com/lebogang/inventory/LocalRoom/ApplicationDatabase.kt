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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lebogang.inventory.LocalRoom.DAOs.ProductDao
import com.lebogang.inventory.LocalRoom.Models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase:RoomDatabase() {

    abstract fun productDao():ProductDao

    companion object{
        @Volatile
        private var DATABASE_INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context):ApplicationDatabase{
            return DATABASE_INSTANCE?: synchronized(this){
                val db = Room.databaseBuilder(context.applicationContext,
                    ApplicationDatabase::class.java, "InventoryDatabase")
                    .build()
                DATABASE_INSTANCE = db
                db
            }
        }
    }
}