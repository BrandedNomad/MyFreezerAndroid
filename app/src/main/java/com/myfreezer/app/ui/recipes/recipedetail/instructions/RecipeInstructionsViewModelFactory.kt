package com.myfreezer.app.ui.recipes.recipedetail.instructions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.ui.recipes.recipedetail.ingredients.RecipeIngredientsViewModel
import java.lang.IllegalArgumentException


/**
 * @class RecipeIngredientsViewModelFactory
 * @description The implementation for a factory that creates and instantiates a viewModel
 * @param {Application} application - the application
 */
class RecipeInstructionsViewModelFactory(val application: Application, val recipeId:Long): ViewModelProvider.Factory {

    /**
     * @method Create
     * @description Creates a new ViewModel Factory instance
     * @param {Class<T>} modelClass - The ViewModel::class.java
     * @return {RecipesViewModel} RecipeIngredientViewModel - a new instance of RecipeIngredientViewModel
     */
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if(modelClass.isAssignableFrom(RecipeInstructionsViewModel::class.java)){
            return RecipeInstructionsViewModel(application,recipeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}