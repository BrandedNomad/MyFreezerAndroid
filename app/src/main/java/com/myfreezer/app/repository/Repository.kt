package com.myfreezer.app.repository

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.local.MyFreezerDatabase

import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.asDomainModel
import com.myfreezer.app.repository.remote.responseclasses.GetRecipesResponse
import com.myfreezer.app.repository.remote.services.Network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.await


/**
 * @class Repository
 * @descrition: contains the implementation for the Repository
 */

class Repository(val database: MyFreezerDatabase) {




    //DATA LISTS

    //populating freezerItem list from the database
    var freezerItemList = Transformations.map(database.freezerDao.getFreezerItems()){
        it.asDomainModel()
    }




    /**
     * @method addFreezerItem
     * @description: Inserts freezerItem into the database
     * @param {FreezerItem} item: The item to insert
     */
    suspend fun addFreezerItem(item:FreezerItem){
        //convert item to databaseFreezerItem
        val itemToInsert: DatabaseFreezerItem = DatabaseFreezerItem(item.name,item.quantity,item.unit,item.minimum,item.dateAddedString)
        database.freezerDao.insert(itemToInsert)


    }

    /**
     * @method deleteFreezerItem
     * @description Deletes freezerItem from the database
     * @param {FreezerItem} item to be deleted
     */
    suspend fun deleteFreezerItem(item:FreezerItem){
        val itemName = item.name
        database.freezerDao.deleteFreezerItem(itemName)

    }

    /**
     * @method updateFreezerItem
     * @description: updates an existing freezerItem when item is edited
     * @param {String} previousId: the id (primary id) of the freezerItem
     * before it was edited, this will be used to find the item in database
     * @param {FreezerItem} freezerItem: the new updated item
     */
    suspend fun updateFreezerItem(previousId:String, freezerItem:FreezerItem){
        database.freezerDao.updateFreezerItem(
            previousId,
            freezerItem.name,
            freezerItem.quantity,
            freezerItem.unit,
            freezerItem.minimum
        )

    }

    fun getFreezerItemListOrderedHighestToLowest():List<FreezerItem>{
        var x = database.freezerDao.getFreezerItemsSortedHighestToLowest().asDomainModel()
        return x


    }

    suspend fun getRecipes():GetRecipesResponse{
        lateinit var response: GetRecipesResponse
        try{
            response = Network.recipesAPI.getRecipes("Chicken").await()
            Log.e("Repo - getRecipes Success",response.toString())
        }catch(exception:Exception){
            Log.e("Repo - getRecipes failed", exception.toString())
        }
        return response
    }


}