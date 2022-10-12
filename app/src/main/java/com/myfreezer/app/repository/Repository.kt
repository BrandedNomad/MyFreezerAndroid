package com.myfreezer.app.repository

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.myfreezer.app.models.*
import com.myfreezer.app.repository.local.database.MyFreezerDatabase

import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseIngredientItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe
import com.myfreezer.app.repository.local.entities.asDomainModel
import com.myfreezer.app.repository.remote.responseclasses.GetRecipesResponse
import com.myfreezer.app.repository.remote.responseclasses.asDomainModel
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

    //populating the recipeList from the databases
    var recipeList =  Transformations.map(database.freezerDao.getRecipes()){
        it.asDomainModel()
    }



    suspend fun getRecipeIngredientList(recipeId:Long):List<IngredientItem>{
        return database.freezerDao.getRecipeIngredientList(recipeId).asDomainModel()
    }


    suspend fun getRecipeInstructionsList(recipeId:Long):List<InstructionItem>{
        Log.e("Repo - inside getRecipeInstructionList",recipeId.toString())
        return database.freezerDao.getRecipeInstructionsList(recipeId).asDomainModel()
    }

    fun getFreezerItemsForFilter(): LiveData<List<FreezerItem>> {
        return Transformations.map(database.freezerDao.getFreezerItems()){
            it.asDomainModel()
        }
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
        database.freezerDao.deleteFreezerItem(itemName)

    }




    /**
     * @method deleteRecipeItem
     * @description Deletes recipe from the database
     * @param {String} itemName - name of item to be deleted
     */
    private suspend fun deleteAllRecipes(itemName: String): Int {
        return database.freezerDao.deleteAllRecipes(itemName)
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
        deleteAllRecipes(previousId)

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

    suspend fun getRecipesFromApi(title:String):GetRecipesResponse{
        //Declare variables
        lateinit var response: GetRecipesResponse
        var tryAgain = true

        //Connect to api
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

        return response

    }

    suspend fun saveRecipesToDataBase(recipesToInsert: List<DatabaseRecipe>):LongArray{
        return database.freezerDao.insertRecipeResults(*recipesToInsert.toTypedArray())

    }



    suspend fun recipeSearch(searchTerm:String){

    }


    /**
     * @method getRecipes
     * @description: Retrieves recipes from the api and saves them in the database
     */
    suspend fun getRecipes(title:String){

        val response = getRecipesFromApi(title)

        //Transform recipes
        val itemToSort = response.asDomainModel(title)


        var recipesToInsert = itemToSort.map{
            it.asDatabaseModelRecipe()
        }

        //save to database
        var listOfRecipeId = saveRecipesToDataBase(recipesToInsert)





        //Insert Ingredients
        var ingredientItemsToInsert = itemToSort.mapIndexed{index,recipeResponse ->

            var id = listOfRecipeId[index]

            recipeResponse.asDatabaseModelIngredients(id.toLong())
        }




        ingredientItemsToInsert.iterator().forEach{ ingredientItemArray ->

            try{
                database.freezerDao.insertIngredientItems(*ingredientItemArray)
            }catch(error:Exception){
                Log.e("ERROR-Repo-insertRecipe",error.toString())
            }

        }

        //Insert Instructions

        var instructionItemsToInsert = itemToSort.mapIndexed{index,recipeResponse ->
            var id = listOfRecipeId[index]
            recipeResponse.asDatabaseModelInstructions(id.toLong())

        }

        instructionItemsToInsert.iterator().forEach{ instructionsItemArray ->

            try{
                database.freezerDao.insertInstructionItems(*instructionsItemArray)
            }catch(error:Exception){
                Log.e("ERROR-Repo-insertInstructions",error.toString())
            }
        }

    }

    suspend fun test(){
        var test = database.freezerDao.getFreezerItemWithRecipes("chicken")

    }

}