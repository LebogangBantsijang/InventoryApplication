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

package com.lebogang.inventory

import android.app.Application
import com.lebogang.inventory.LocalRoom.ApplicationDatabase
import com.lebogang.inventory.LocalRoom.ApplicationRepository

class InventoryApplication:Application() {
    private val localDataBase: ApplicationDatabase by lazy {
        ApplicationDatabase.getDatabase(this)
    }
    val localRepository:ApplicationRepository by lazy {
        ApplicationRepository(localDataBase.productDao())
    }
}