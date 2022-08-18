package com.myfreezer.app.ui.freezer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.repository.Repository
import kotlinx.coroutines.launch

/**
 * @class FreezerViewModel
 * @Description Contains the implementation for the FreezerViewModel
 */
class FreezerViewModel(application: Application): ViewModel() {

    //TODO: Create database and Repo
    private val repository = Repository()

    //TODO: Create live data

    init{
        repository.fakeload()
    }


    //TODO: populate data list
    var freezerItemList = repository.freezerItemList

    //TODO:Setup nav triggers



    override fun onCleared() {
        super.onCleared()
    }
}