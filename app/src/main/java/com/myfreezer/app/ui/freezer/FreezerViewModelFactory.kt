package com.myfreezer.app.ui.freezer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * @class FreezerViewModelFactory
 * @description The implementation for a factory that creates and instantiates a viewModel
 * @param {Application} application - the application
 */
class FreezerViewModelFactory(val application: Application): ViewModelProvider.Factory {

    /**
     * @method Create
     * @description Creates a new ViewModel Factory instance
     * @param {Class<T>} modelClass - The ViewModel::class.java
     * @return {FreezerViewModel FreezerViewModel - a new instance of FreezerViewModel
     */
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if(modelClass.isAssignableFrom(FreezerViewModel::class.java)){
            return FreezerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}