package com.myfreezer.app.ui.recipes.recipedetail.instructions

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.models.InstructionItem
import com.myfreezer.app.repository.Repository
import com.myfreezer.app.repository.local.database.MyFreezerDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeInstructionsViewModel(application: Application, recipeId:Long): ViewModel() {

    //Declare variables

    //Repo setup
    var database = MyFreezerDatabase.getDatabase(application)
    var repository = Repository(database)

    //Exception handling setup

    var coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    //dataflows
    private var _instructionsList = MutableLiveData<List<InstructionItem>>()
    val instructionsList:LiveData<List<InstructionItem>>
        get() = _instructionsList

    //init
    init{
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            _instructionsList.value = repository.getRecipeInstructionsList(recipeId)
        }

    }

    //methods

    //Cleanup

    override fun onCleared() {
        super.onCleared()
    }
}