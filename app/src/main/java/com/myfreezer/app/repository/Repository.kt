package com.myfreezer.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myfreezer.app.domain.FreezerItem
import com.myfreezer.app.shared.freezerList


/**
 * @class Repository
 * @descrition: contains the implementation for the Repository
 */
//val database:FreezerDatabase
class Repository {

    private var _dummylist = MutableLiveData<List<FreezerItem>?>()
    val dummylist: LiveData<List<FreezerItem>?>
        get() = _dummylist

    //Data lists
    val freezerItemList = dummylist

    fun fakeload(){
        _dummylist.value = freezerList
    }
}