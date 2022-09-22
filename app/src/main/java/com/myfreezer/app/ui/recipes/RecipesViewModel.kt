package com.myfreezer.app.ui.recipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.MyFreezerDatabase
import com.myfreezer.app.repository.remote.responseclasses.GetRecipesResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class RecipesViewModel(application: Application): ViewModel(){
    //DRED-MC

    //Declare variables

    //Setup repository
    private val database = MyFreezerDatabase.getDatabase(application)
    private val repository = Repository(database)

    //Setup exceptions handling
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Setup Data Flows

    var recipeList = repository.recipeList


    //Initialization
    init {




    }

    //Methods






    //Cleanup
}