package com.myfreezer.app.ui.recipes

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.database.MyFreezerDatabase
import com.myfreezer.app.repository.local.entities.asDomainModel
import kotlinx.coroutines.*

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
    private var _navigationTrigger = MutableLiveData<RecipeItem?>()
    val navigationTrigger: LiveData<RecipeItem?>
        get() = _navigationTrigger

    private var _recipesFilter = MutableLiveData<MutableList<String>>()
    val recipesFilter: LiveData<MutableList<String>>
        get() = _recipesFilter


    //Initialization
    init {
        viewModelScope.launch {
            repository.test()
        }

        _recipesFilter.value = mutableListOf()


    }

    //Methods

    fun addToFilter(name:String){
        Log.e("AddToFilter in ViewModel","inside")
        var list = _recipesFilter.value
        list?.add(name)
        _recipesFilter.postValue(list!!)
        Log.e("_reicpesFilterContent",_recipesFilter.value.toString())
    }

    fun removeFromFilter(name:String){
        Log.e("removeFromFilter in ViewModel","inside")
        var list = _recipesFilter.value
        list?.remove(name)
        _recipesFilter.postValue(list!!)
        Log.e("_reicpesFilterContent",_recipesFilter.value.toString())
    }

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


    fun getFreezerItemList():LiveData<List<FreezerItem>>{
        Log.e("ViewModel","inside")
        return repository.getFreezerItemsForFilter()
    }




    fun test(){
        Log.e("Test","yip it is working")
    }

    /**
     * @method onCleared()
     * @description lifecycle method that handles cleanup of tasks
     */
    override fun onCleared() {
        super.onCleared()
    }


}