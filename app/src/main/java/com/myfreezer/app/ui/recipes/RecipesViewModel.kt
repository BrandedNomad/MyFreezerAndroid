package com.myfreezer.app.ui.recipes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.database.MyFreezerDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * @class RecipesViewModel
 * @description Contains the implementation for the RecipesViewModel
 * @param {Application} application - The Application
 */
class RecipesViewModel(application: Application): ViewModel(){
    //Declare variables

    //Setup repository
    private val database = MyFreezerDatabase.getDatabase(application)
    private val repository = Repository(database)


    //Setup exceptions handling
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Setup Data Flows

    //The list of reicpes to display
    var recipeList = repository.recipeList

    //Navigation trigger used to navigate to specific recipe
    private var _navigationTrigger = MutableLiveData<RecipeItem>()
    val navigationTrigger: LiveData<RecipeItem>
        get() = _navigationTrigger


    //Initialization
    init {
        viewModelScope.launch {
            repository.test()
        }


    }

    //Methods

    /**
     * @method navigate
     * @description sets navigation trigger with the value of the recipe selected. This triggers an observer
     * in the fragment which then handles the navigation
     * @param {RecipeItem} recipe - the selected recipe
     */
    fun navigate(recipe:RecipeItem){
        _navigationTrigger.value = recipe
    }


    /**
     * @method doneNavigating
     * @description sets navigation trigger to null after navigation is complete
     */
    fun doneNavigating(){
        _navigationTrigger.value = null
    }

    /**
     * @method onCleared()
     * @description lifecycle method that handles cleanup of tasks
     */
    override fun onCleared() {
        super.onCleared()
    }
}