package com.myfreezer.app.repository

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.local.DatabaseFreezerItem
import com.myfreezer.app.repository.local.FreezerItemDatabase
import com.myfreezer.app.repository.local.asDomainModel
import com.myfreezer.app.shared.freezerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @class Repository
 * @descrition: contains the implementation for the Repository
 */

class Repository(val database: FreezerItemDatabase) {




    //DATA LISTS

    //populating freezerItem list from the database
    val freezerItemList = Transformations.map(database.freezerDao.getFreezerItems()){
        it.asDomainModel()
    }

    /**
     * @method addFreezerItem
     * @description: Inserts freezerItem into the database
     * @param {FreezerItem} item: The item to insert
     */
    suspend fun addFreezerItem(item:FreezerItem){
        withContext(Dispatchers.IO){
            //convert item to databaseFreezerItem
            val itemToInsert:DatabaseFreezerItem = DatabaseFreezerItem(item.name,item.quantity,item.unit,item.minimum)
            database.freezerDao.insert(itemToInsert)
        }

    }

    /**
     * @method deleteFreezerItem
     * @description Deletes freezerItem from the database
     * @param {FreezerItem} item to be deleted
     */
    suspend fun deleteFreezerItem(item:FreezerItem){
        withContext(Dispatchers.IO){

            val itemName = item.name
            database.freezerDao.deleteFreezerItem(itemName)
        }
    }


}