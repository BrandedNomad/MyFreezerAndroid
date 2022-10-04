package com.myfreezer.app.repository.local.database

import android.content.Context
import androidx.room.*
import com.myfreezer.app.repository.local.dao.FreezerDao
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseIngredientItem
import com.myfreezer.app.repository.local.entities.DatabaseInstructionItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe

/**
 * @class MyFreezerDatabase
 * @description: Contains the implementation of a room database
 */
@Database(entities=[DatabaseFreezerItem::class,DatabaseRecipe::class,DatabaseIngredientItem::class,DatabaseInstructionItem::class],version = 2)
abstract class MyFreezerDatabase:RoomDatabase() {
    //The Data access object used to interact with the database
    abstract val freezerDao: FreezerDao

    companion object{
        //An instance of the database
        @Volatile
        private lateinit var INSTANCE: MyFreezerDatabase

        /**
         * @method getDatabase
         * @description: getDatabase will check whether a database instance has already been created,
         * if it has then it will simply return the created instance, if not then it will create a new instance
         * @param {Context} context - the application context
         */
        fun getDatabase(context: Context): MyFreezerDatabase {

            //a lock is implemented to prevent race conditions
            synchronized(this){
                if(!Companion::INSTANCE.isInitialized){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyFreezerDatabase::class.java,
                        "MyFreezerDatabase"
                    ).build()

                    INSTANCE = instance
                }
                return INSTANCE
            }
        }
    }

}