package com.myfreezer.app.ui.recipes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.FreezerItemDatabase
import kotlinx.coroutines.CoroutineExceptionHandler

class RecipesViewModel(application: Application): ViewModel(){
    //DRED-MC

    //Declare variables

    //Setup repository
    private val database = FreezerItemDatabase.getDatabase(application)
    private val repository = Repository(database)

    //Setup exceptions handling
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Setup Data Flows

    //Initialization
    init {

    }

    //Methods

    //Cleanup
}