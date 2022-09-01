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

    /**
     * @method updateFreezerItem
     * @description: updates existing freezer item
     * @param {String} previousId: the id of the freezerItem before it was edited
     * @param {FreezerItem} item: The item to be deleted
     */
    fun updateFreezerItem(previousId:String, freezerItem:FreezerItem) = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler){
        repository.updateFreezerItem(previousId,freezerItem)

    }

    /**
     * @method incrementFreezerItem
     * @description Function used by the quick edit button to increment freezer item quantity by one
     * @param {String} previousId: the id of the item being incremented
     * @param {FreezerItem} freezerItem: the item being incremented
     */
    fun incrementFreezerItem(previousId:String, freezerItem:FreezerItem){

        freezerItem.quantity = freezerItem.quantity + 1
        updateFreezerItem(previousId,freezerItem)

    }


    /**
     * @method decrementFreezerItem
     * @description Function used by the quick edit button to decrement freezer item quantity by one
     * @param {String} previousId: the id of the item being decremented
     * @param {FreezerItem} freezerItem: the item being decremented
     */
    fun decrementFreezerItem(previousId:String, freezerItem:FreezerItem){

        //if quantity is more than 1 then minus 1
        if(freezerItem.quantity > 1){
            freezerItem.quantity -= 1
            updateFreezerItem(previousId,freezerItem)
        } else {
            //if it is 1 then delete it and remove it from the list
            deleteFreezerItem(freezerItem)
        }


    }



    //TODO:Setup nav triggers



    //cleans up after the viewModel has been destroyed
    override fun onCleared() {
        super.onCleared()
    }
}