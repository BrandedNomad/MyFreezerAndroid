package com.myfreezer.app.ui.recipes.recipedetail.ingredients

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.myfreezer.app.models.IngredientItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.database.MyFreezerDatabase
import com.myfreezer.app.repository.local.database.MyFreezerDatabase_Impl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeIngredientsViewModel(application: Application, recipeId:Long):ViewModel() {

    //Declare variables

    //Repo setup
    val database = MyFreezerDatabase.getDatabase(application)
    val repository = Repository(database)

    //exception handler setup
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    //Dataflow setup
    private var _ingredientList = MutableLiveData<List<IngredientItem>>()
    val ingredientList: LiveData<List<IngredientItem>>
        get() = _ingredientList


    //Initiate
    init{
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler){
            _ingredientList.value = repository.getRecipeIngredientList(recipeId)
            Log.e("ViewModel - list populated","Maybe")
        }

    }


    //Methods

    override fun onCleared() {
        super.onCleared()
    }
}