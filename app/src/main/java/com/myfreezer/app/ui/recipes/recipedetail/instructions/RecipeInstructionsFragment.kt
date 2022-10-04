package com.myfreezer.app.ui.recipes.recipedetail.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeInstructionsBinding
import com.myfreezer.app.models.RecipeItem


/**
 * @class RecipeInstructionsFragment
 * @description Contains the implementation for the RecipeInstructionsFragment
 */
class RecipeInstructionsFragment: Fragment() {

    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return {FragmentRecipeInstructionsBinding} binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Create databinding
        val binding:FragmentRecipeInstructionsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_instructions,
            container,
            false
        )

        //get recipeId
        var recipeItem: RecipeItem = arguments?.getParcelable("RecipeItem")!!
        var recipeId = recipeItem.recipeId


        //ViewModel

        var application = requireNotNull(activity).application
        var viewModelFactory = RecipeInstructionsViewModelFactory(application,recipeId)
        var viewModel = ViewModelProvider(this,viewModelFactory)[RecipeInstructionsViewModel::class.java]


        var adapter = RecipeInstructionsAdapter(RecipeInstructionsAdapter.OnClickListener{

        })

        viewModel.instructionsList.observe(viewLifecycleOwner, Observer{
            adapter.submitList(it)
        })



        binding.recipeInstructionsRecyclerView.adapter = adapter



        //return view
        return binding.root
    }
}