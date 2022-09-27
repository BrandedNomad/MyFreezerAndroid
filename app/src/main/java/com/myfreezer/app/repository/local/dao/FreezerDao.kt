package com.myfreezer.app.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe

/**
 * @interface FreezerDao
 * @description The data access object used to interact with the database.
 */
@Dao
interface FreezerDao {

    /**
     * @method insert
     * @description inserts a single freezer item into the database
     */
    @Insert
    suspend fun insert(freezerItem: DatabaseFreezerItem)

    /**
     * @method inserAll
     * @description inserts all the supplied freezerItems into database
     * @param {*List<DatabaseFreezerItem>} freezerItem - the list of items to insert (use spread operator)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg freezerItem: DatabaseFreezerItem)

    /**
     * @method insertRecipeResults
     * @description Inserts all recipes returned from api into database
     * @param {*List<DatabaseRecipe>} recipe - the list of recipes returned from api (use spread operator)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeResults(vararg recipe: DatabaseRecipe)

    /**
     * @method getFreezerItems
     * @description returns a list of freezer Items as livedata in alphabetical order
     * @return {LiveData<List<DatabaseFreezerItem>>} - the results of query
     */
    @Query("SELECT * FROM databasefreezeritem ORDER BY name")
    fun getFreezerItems(): LiveData<List<DatabaseFreezerItem>>

    /**
     * @method getRecipes
     * @description returns a list of recipes from database as livedata
     * @return {LiveData<List<DatabaseRecipe>>} - the query result
     */
    @Query("SELECT * FROM databaserecipe ORDER BY title")
    fun getRecipes(): LiveData<List<DatabaseRecipe>>

    /**
     * @method deleteFreezerItem
     * @description deletes item from database
     * @param {String} itemName - the primaryKey of item
     */
    @Query("DELETE FROM databasefreezeritem WHERE name = :itemName")
    suspend fun deleteFreezerItem(itemName: String)

    /**
     * @method deleteAllRecipes
     * @description Deletes all recipes associated with a freezerItem
     * @param {String} freezerItem - the freezerItem associated with recipe
     */
    @Query("DELETE FROM databaserecipe WHERE freezerItem_ID = :freezerItem")
    suspend fun deleteAllRecipes(freezerItem: String)

    /**
     * @method updateFreezerItem
     * @description updates an existing freezer item with new information
     * @param {String} previousId - the itemName before the item was edited.
     * @param {Sting} name - the new item name.
     * @param {Int} quantity - the new quantity
     * @param {String} unit - the new unit
     * @param {minimum} Int - the new minimum level
     */
    @Query(
        "UPDATE databasefreezeritem " +
                "SET name = :name, quantity = :quantity, unit = :unit, minimum = :minimum " +
                "WHERE name = :previousId"
    )
    suspend fun updateFreezerItem(
        previousId: String,
        name: String,
        quantity: Int,
        unit: String,
        minimum: Int
    )

}