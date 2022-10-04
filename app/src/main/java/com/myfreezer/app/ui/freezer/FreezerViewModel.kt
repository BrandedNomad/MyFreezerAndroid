package com.myfreezer.app.ui.freezer

import android.app.Application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.database.MyFreezerDatabase

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @class FreezerViewModel
 * @Description Contains the implementation for the FreezerViewModel

 * @param {Application} application - The application

 */
class FreezerViewModel(application: Application): ViewModel() {

    //variables
    var isContextMenuOpen = false

    //Setup exception handling for coroutines
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Get Repository

    private val database = MyFreezerDatabase.getDatabase(application)
    private val repository = Repository(database)


    //populate the freezer item list with the items stored in database
    var freezerItemList = repository.freezerItemList

    //LiveData used to set the list sorting flag

    private var _sortListBy = MutableLiveData<String>()
    val sortListBy:LiveData<String>
        get() = _sortListBy


    //Initialize variables
    init{
        isContextMenuOpen = false
        _sortListBy.value = "alpha"

    }


    /**
     * @method addItem
     * @description: Adds a new item to the list by saving it to the database. GlobalScope is used instead of viewModelScope
     * so that the coroutine is not canceled when navigating away from the freezerView.
     * @param {FreezerItem} item: The new item to be saved
     */
    fun addItem(item: FreezerItem) = GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

        repository.addFreezerItem(item)

    }

    /**
     * @method deleteFreezerItem

     * @description: Deletes freezer item. GlobalScope is used instead of viewModelScope
     * so that the coroutine is not canceled when navigating away from the freezerView.
     * @param {FreezerItem} item: The item to be deleted
     */
    fun deleteFreezerItem(item:FreezerItem) = GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler){

        repository.deleteFreezerItem(item)
    }

    /**
     * @method updateFreezerItem

     * @description: updates existing freezer item. GlobalScope is used instead of viewModelScope
     * so that the coroutine is not canceled when navigating away from the freezerView.
     * @param {String} previousId: the id of the freezerItem before it was edited
     * @param {FreezerItem} item: The item to be deleted
     */
    fun updateFreezerItem(previousId:String, freezerItem:FreezerItem) = GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler){

        repository.updateFreezerItem(previousId,freezerItem)

    }

    /**
     * @method incrementFreezerItem
     * @description Function used by the quick edit button to increment freezer item quantity by one
     * @param {String} previousId: the id of the item being incremented
     * @param {FreezerItem} freezerItem: the item being incremented
     */
    fun incrementFreezerItem(previousId:String, freezerItem:FreezerItem){

        if(freezerItem.quantity < 999){
            freezerItem.quantity = freezerItem.quantity + 1
            updateFreezerItem(previousId,freezerItem)
        }

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

    /**
     * @method triggerContextMenuFlow
     * @description: Emits the status of context menu to interested modules (the adapter)
     * @return {Flow} an observable
     */
    fun triggerContextMenuFlow(): Flow<Boolean?> {
       return flow {
           while(true){
               emit(isContextMenuOpen)
               delay(1000)
           }

       }
    }

    /**
     * @method setContextMenuOpen
     * @description sets the isContextMenuOpen flag to true
     */
    fun setContextMenuOpen(){
        isContextMenuOpen = true
    }

    /**
     * @method setContextMenuClosed
     * @description sets the isContextMenuOpen flag to false
     */
    fun setContextMenuClosed(){
        isContextMenuOpen = false
    }


    /**
     * @method sortList
     * @description When the user selects a filter option, it sorts the list of FreezerItems retrieved from the database in accordance with
     * the user's preference
     * @return {List<FreezerItem>} the sorted list
     */
    fun sortList():List<FreezerItem>?{

        //The list to be sorted
        var sortedList = freezerItemList.value


        if(sortListBy.value == "lowest"){
            //Sort from lowest to highest quantity

            freezerItemList.value?.let{
                sortedList = it.sortedBy{it.quantity}
            }
        }else if(sortListBy.value == "highest"){

            //Sort from highest to lowest
            freezerItemList.value?.let{
                sortedList = it.sortedByDescending{it.quantity}
            }
        }else if(sortListBy.value == "alpha"){
            //Sort in alphabetical order

            freezerItemList.value?.let{
                sortedList = it.sortedBy{it.name}
            }
        }else if(sortListBy.value =="oldest"){

            //Sort from oldest to latest
            freezerItemList.value?.let{
                sortedList = it.sortedBy{ it.dateAdded }
            }
        }else if(sortListBy.value == "latest"){
            //Sort from latest to oldest

            freezerItemList.value?.let{
                sortedList = it.sortedByDescending{it.dateAdded}
            }
        }else {
            //return the list as is (alphabetically sorted)

            freezerItemList.value?.let{
                sortedList = it
            }
        }
        return sortedList
    }


    /**
     * @method setSortListBy
     * @description sets the value to filter option selected by user
     */

    fun setSortListBy(sortBy:String){
        _sortListBy.value = sortBy

    }



    /**
     * @method onCleared()
     * @description lifeCycle method that performs cleanup tasks
     * @
     */

    override fun onCleared() {
        super.onCleared()
    }
}