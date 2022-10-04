package com.myfreezer.app.ui.recipes.recipedetail.ingredients

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeIngredientsBinding
import com.myfreezer.app.models.RecipeItem


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

        var recipeItem: RecipeItem = arguments?.getParcelable("RecipeItem")!!
        var recipeId:Long = recipeItem.recipeId

        //create databinding
        val binding: FragmentRecipeIngredientsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_ingredients,
            container,
            false
        )

        var application = requireNotNull(this.activity).application
        var viewModelFactory = RecipeIngredientsViewModelFactory(application,recipeId)
        var viewModel = ViewModelProvider(this,viewModelFactory)[RecipeIngredientsViewModel::class.java]

        var adapter = RecipeIngredientsAdapter(RecipeIngredientsAdapter.OnClickListener{

        })

        viewModel.ingredientList.observe(this.viewLifecycleOwner, Observer{
            Log.e("Fragment - inside observer",it.toString())
            adapter.submitList(it)
        })


        binding.ingredientsRecyclerView.adapter = adapter



        //return view
        return binding.root
    }
}