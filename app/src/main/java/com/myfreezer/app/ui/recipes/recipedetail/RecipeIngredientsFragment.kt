package com.myfreezer.app.ui.recipes.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeIngredientsBinding

/**
 * @class RecipeIngredientsFragment
 * @description Contains the implementation for the RecipeIngredientsFragment
 */
class RecipeIngredientsFragment: Fragment() {

    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return {FragmentRecipeIngredientBinding} binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //create databinding
        val binding: FragmentRecipeIngredientsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_ingredients,
            container,
            false
        )

        //return view
        return binding.root
    }
}