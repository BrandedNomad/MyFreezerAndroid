package com.myfreezer.app.repository.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.local.dao.FreezerDao
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe


@Database(entities=[DatabaseFreezerItem::class,DatabaseRecipe::class],version = 2)
abstract class MyFreezerDatabase:RoomDatabase() {

    abstract val freezerDao: FreezerDao

    companion object{
        @Volatile
        private lateinit var INSTANCE: MyFreezerDatabase

        fun getDatabase(context: Context):MyFreezerDatabase{

            synchronized(this){
                if(!::INSTANCE.isInitialized){
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