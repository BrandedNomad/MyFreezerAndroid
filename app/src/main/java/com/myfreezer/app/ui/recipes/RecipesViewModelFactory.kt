package com.myfreezer.app.ui.recipes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * @class RecipesViewModelFactory
 * @description The implementation for a factory that creates and instantiates a viewModel
 * @param {Application} application - the application
 */
class RecipesViewModelFactory(val application: Application): ViewModelProvider.Factory {

    /**
     * @method Create
     * @description Creates a new ViewModel Factory instance
     * @param {Class<T>} modelClass - The ViewModel::class.java
     * @return {RecipesViewModel} RecipesViewModel - a new instance of RecipesViewModel
     */
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if(modelClass.isAssignableFrom(RecipesViewModel::class.java)){
            return RecipesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}