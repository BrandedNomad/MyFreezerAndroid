package com.myfreezer.app.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipesBinding
import com.myfreezer.app.ui.main.Communicator



/**
 * @class RecipesFragment
 * @description Contains the implementation for the RecipesFragment
 */
class RecipesFragment: Fragment() {

    //Declare Variables
    lateinit var viewModel:RecipesViewModel
    lateinit var adapter:RecipesAdapter

    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return {FragmentRecipesBinding} binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Databinding
        val binding: FragmentRecipesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipes,
            container,
            false
        )

        binding.lifecycleOwner = this


        //Setup viewModel
        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecipesViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory)[RecipesViewModel::class.java]

        //setup adapter
        adapter = RecipesAdapter(RecipesAdapter.OnClickListener{
            //Navigate to recipe detail view
            viewModel.navigate(it)
        })

        binding.recipesRecyclerView.adapter = adapter

        //Set Observers
        //Watch for recipes
        viewModel.recipeList.observe(viewLifecycleOwner,Observer{

            if(it.size > 0){ //If the list contains recipes
                //Remove the "no recipes" background message and display the list of recipes
                binding.recipeEmptyMessage.setVisibility(View.GONE)
                adapter.submitList(it)
            }else{
                //Display background "no recipes" message
                binding.recipeEmptyMessage.setVisibility(View.VISIBLE)
            }

        })

        //When the navigation trigger changes
        viewModel.navigationTrigger.observe(viewLifecycleOwner,Observer{
            //recipe has been selected
            if(it != null){
                //Get communicator class
                val communicator = requireActivity() as Communicator
                //reset navigation trigger before navigating
                viewModel.doneNavigating()
                //navigate to details fragment
                communicator.transferData(it)
            }
        })

        //return view
        return binding.root
    }

}

