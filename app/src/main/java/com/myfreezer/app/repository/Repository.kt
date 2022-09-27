package com.myfreezer.app.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.local.database.MyFreezerDatabase

import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.asDomainModel
import com.myfreezer.app.repository.remote.responseclasses.GetRecipesResponse
import com.myfreezer.app.repository.remote.responseclasses.asDataBaseModel
import com.myfreezer.app.repository.remote.services.Network

import retrofit2.await


/**
 * @class Repository
 * @description: contains the implementation for the Repository which acts as a single source of truth for this application
 * @param {MyFreezerDatabase} database - the application database
 */

class Repository(val database: MyFreezerDatabase) {


    //populating freezerItem list from the database
    var freezerItemList = Transformations.map(database.freezerDao.getFreezerItems()){
        it.asDomainModel()
    }

    //populating the recipeList from the database
    var recipeList =  Transformations.map(database.freezerDao.getRecipes()){
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

        //insert item into database
        database.freezerDao.insert(itemToInsert)

        //Get recipes suggestions for the item and insert them into the database
        getRecipes(item.name)
    }

    /**
     * @method deleteFreezerItem
     * @description Deletes freezerItem from the database
     * @param {FreezerItem} item to be deleted
     */
    suspend fun deleteFreezerItem(item:FreezerItem){
        //Get the primary key
        val itemName = item.name


        //delete all associated recipes
        database.freezerDao.deleteAllRecipes(itemName).run {
            //delete the freezerItem
            database.freezerDao.deleteFreezerItem(itemName)
        }





    }

    /**
     * @method updateFreezerItem
     * @description: updates an existing freezerItem when item is edited
     * @param {String} previousId: the id (primary id) of the freezerItem
     * before it was edited, this will be used to find the item in database
     * @param {FreezerItem} freezerItem: the new updated item
     */
    suspend fun updateFreezerItem(previousId:String, freezerItem:FreezerItem){

        //delete recipes which are associated with this item
        database.freezerDao.deleteAllRecipes(previousId)

        //update the item
        database.freezerDao.updateFreezerItem(
            previousId,
            freezerItem.name,
            freezerItem.quantity,
            freezerItem.unit,
            freezerItem.minimum
        )

        //Get new recipe suggestions for updated item
        getRecipes(freezerItem.name)

    }


    /**
     * @method getRecipes
     * @description: Retrieves recipes from the api and saves them in the database
     */
    suspend fun getRecipes(title:String){
        //Declare variables
        lateinit var response: GetRecipesResponse
        var tryAgain = true

        //while unsuccessful
        while(tryAgain){
            try{
                //Get recipes from the api
                response = Network.recipesAPI.getRecipes(title).await()
                tryAgain = false
            }catch(exception:Exception){
                //if failed, then try again
                tryAgain = true
            }
        }

        //Create database model
        val itemsToInsert = response.asDataBaseModel(title)
        //save to database
        database.freezerDao.insertRecipeResults(*itemsToInsert)
    }
}