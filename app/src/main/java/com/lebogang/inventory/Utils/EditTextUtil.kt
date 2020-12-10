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

package com.lebogang.inventory.Utils

class EditTextUtil {

    companion object{
        fun getErrorMessage(errorTypes: ErrorTypes):String{
            when(errorTypes){
                ErrorTypes.NULL_VALUES -> "Details not complete"
                ErrorTypes.INVALID_PASSWORD -> "Incorrect Password"
                ErrorTypes.UNCONFIRMED_PASSWORD -> "Passwords do not match"
                ErrorTypes.UNKNOWN -> "Fatal Error"
            }
            return ""
        }
    }

    enum class ErrorTypes{
        NULL_VALUES, INVALID_PASSWORD,UNCONFIRMED_PASSWORD, UNKNOWN
    }
}