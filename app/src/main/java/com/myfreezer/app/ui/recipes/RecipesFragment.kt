package com.myfreezer.app.ui.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.databinding.FragmentRecipesBinding
import com.myfreezer.app.shared.mock.mockDataList
import com.myfreezer.app.ui.freezer.FreezerViewModel
import com.myfreezer.app.ui.freezer.FreezerViewModelFactory
import kotlinx.coroutines.flow.collectLatest

class RecipesFragment: Fragment() {

    //Declare Variables
    lateinit var viewModel:RecipesViewModel
    lateinit var adapter:RecipesAdapter

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



        //LAYOUTS

        //VIEWMODEL

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecipesViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory)[RecipesViewModel::class.java]

        //ADAPTER
        adapter = RecipesAdapter(RecipesAdapter.OnClickListener{
            //Do nothing
        })

        binding.recipesRecyclerView.adapter = adapter

        //adapter.submitList(mockDataList)

        //OBSERVERS
        viewModel._recipeList.observe(viewLifecycleOwner,Observer{
            adapter.submitList(it)
            Log.e("Fragment - recipeListObserver",it.toString())
        })



        viewModel.getRecipes()
        return binding.root
    }
}