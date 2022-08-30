package com.myfreezer.app.ui.freezer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.FreezerItemDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @class FreezerViewModel
 * @Description Contains the implementation for the FreezerViewModel
 */
class FreezerViewModel(application: Application): ViewModel() {

    //Setup exception handling for coroutines
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Get Repository
    private val database = FreezerItemDatabase.getDatabase(application)
    private val repository = Repository(database)

    //TODO: Create live data

    init{
        //TODO
    }


    //populate the freezer item list with the items stored in database
    var freezerItemList = repository.freezerItemList

    /**
     * @method addItem
     * @description: Adds a new item to the list by saving it to the database
     * @param {FreezerItem} item: The new item to be saved
     */
    fun addItem(item: FreezerItem) = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
        repository.addFreezerItem(item)
    }

    /**
     * @method deleteFreezerItem
     * @description: Deletes freezer item
     * @param {FreezerItem} item: The item to be deleted
     */
    fun deleteFreezerItem(item:FreezerItem) = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler){
        repository.deleteFreezerItem(item)
    }

    //TODO:Setup nav triggers



    //cleans up after the viewModel has been destroyed
    override fun onCleared() {
        super.onCleared()
    }
}