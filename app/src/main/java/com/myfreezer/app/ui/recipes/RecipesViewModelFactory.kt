package com.myfreezer.app.ui.recipes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RecipesViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if(modelClass.isAssignableFrom(RecipesViewModel::class.java)){
            return RecipesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}