package com.myfreezer.app.ui.recipes.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeIngredientsBinding
import com.myfreezer.app.databinding.FragmentRecipeInstructionsBinding

class RecipeIngredientsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentRecipeIngredientsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_ingredients,
            container,
            false
        )
        return binding.root
    }
}