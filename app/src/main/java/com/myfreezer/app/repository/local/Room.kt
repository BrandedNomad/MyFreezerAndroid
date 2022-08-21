package com.myfreezer.app.repository.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FreezerDao {

    @Insert
    suspend fun insert(freezerItem:DatabaseFreezerItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg freezerItem:DatabaseFreezerItem)

    @Query("SELECT * FROM databasefreezeritem")
    fun getFreezerItems(): LiveData<List<DatabaseFreezerItem>>
}

@Database(entities=[DatabaseFreezerItem::class],version = 1)
abstract class FreezerItemDatabase:RoomDatabase() {

    abstract val freezerDao:FreezerDao

    companion object{
        private lateinit var INSTANCE: FreezerItemDatabase

        fun getDatabase(context: Context):FreezerItemDatabase{

            if(!::INSTANCE.isInitialized){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FreezerItemDatabase::class.java,
                    "freezeritemdatabase"
                ).build()

                INSTANCE = instance
            }
            return INSTANCE
        }
    }

}