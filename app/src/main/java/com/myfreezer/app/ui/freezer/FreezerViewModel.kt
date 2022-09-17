package com.myfreezer.app.ui.freezer

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.FreezerItemDatabase
import com.myfreezer.app.shared.utils.Utils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @class FreezerViewModel
 * @Description Contains the implementation for the FreezerViewModel
 */
class FreezerViewModel(application: Application): ViewModel() {

    //variables
    var isContextMenuOpen = false

    //Setup exception handling for coroutines
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Get Repository
    private val database = FreezerItemDatabase.getDatabase(application)
    private val repository = Repository(database)


    //TODO: Create live data
    //populate the freezer item list with the items stored in database
    var freezerItemList = repository.freezerItemList



    private var _sortListBy = MutableLiveData<String>()
    val sortListBy:LiveData<String>
        get() = _sortListBy




    init{
        //TODO
        isContextMenuOpen = false
        _sortListBy.value = "alpha"


    }








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

    //sorting functions
    fun sortList():List<FreezerItem>?{

        var sortedList = freezerItemList.value

        if(sortListBy.value == "lowest"){
            freezerItemList.value?.let{
                sortedList = it.sortedBy{it.quantity}
            }
        }else if(sortListBy.value == "highest"){
            freezerItemList.value?.let{
                sortedList = it.sortedByDescending{it.quantity}
            }

        }else if(sortListBy.value == "alpha"){
            freezerItemList.value?.let{
                sortedList = it.sortedBy{it.name}
            }
        }else if(sortListBy.value =="oldest"){
            //do nothing
            freezerItemList.value?.let{

                sortedList = it.sortedBy{ it.dateAdded }
            }

        }else if(sortListBy.value == "latest"){
            //do nothing
            freezerItemList.value?.let{
                sortedList = it.sortedByDescending{it.dateAdded}
            }
        }else {
            freezerItemList.value?.let{
                sortedList = it
            }
        }
        return sortedList
    }

    fun setSortListBy(sortBy:String){
        _sortListBy.value = sortBy

    }



    //TODO:Setup nav triggers



    //cleans up after the viewModel has been destroyed
    override fun onCleared() {
        super.onCleared()
    }
}