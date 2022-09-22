package com.myfreezer.app.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe


@Dao
interface FreezerDao {

    @Insert
    suspend fun insert(freezerItem: DatabaseFreezerItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg freezerItem: DatabaseFreezerItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeResults(vararg recipe: DatabaseRecipe)

    @Query("SELECT * FROM databasefreezeritem ORDER BY name")
    fun getFreezerItems(): LiveData<List<DatabaseFreezerItem>>

    @Query("SELECT * FROM databasefreezeritem ORDER BY quantity DESC")
    fun getFreezerItemsSortedHighestToLowest(): List<DatabaseFreezerItem>

    @Query("SELECT * FROM databaserecipe ORDER BY title")
    fun getRecipes(): LiveData<List<DatabaseRecipe>>

    @Query("DELETE FROM databasefreezeritem WHERE name = :itemName")
    suspend fun deleteFreezerItem(itemName: String)

    @Query("DELETE FROM databaserecipe WHERE freezerItem_ID = :freezerItem")
    suspend fun deleteAllRecipes(freezerItem: String)

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