package com.myfreezer.app.ui.freezer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FreezerViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if(modelClass.isAssignableFrom(FreezerViewModel::class.java)){
            return FreezerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}