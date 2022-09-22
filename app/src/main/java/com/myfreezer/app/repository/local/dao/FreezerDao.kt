package com.myfreezer.app.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem


@Dao
interface FreezerDao {

    @Insert
    suspend fun insert(freezerItem: DatabaseFreezerItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg freezerItem: DatabaseFreezerItem)

    @Query("SELECT * FROM databasefreezeritem ORDER BY name")
    fun getFreezerItems(): LiveData<List<DatabaseFreezerItem>>

    @Query("SELECT * FROM databasefreezeritem ORDER BY quantity DESC")
    fun getFreezerItemsSortedHighestToLowest(): List<DatabaseFreezerItem>

    @Query("DELETE FROM databasefreezeritem WHERE name = :itemName")
    suspend fun deleteFreezerItem(itemName: String)

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