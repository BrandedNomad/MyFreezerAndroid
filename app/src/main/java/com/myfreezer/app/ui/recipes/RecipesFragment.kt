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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipesBinding
import com.myfreezer.app.ui.main.Communicator
import com.myfreezer.app.ui.recipes.recipedetail.RecipeDetailFragment



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

        //INITIALIZE



        //LAYOUTS

        //VIEWMODEL

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecipesViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory)[RecipesViewModel::class.java]

        //ADAPTER
        adapter = RecipesAdapter(RecipesAdapter.OnClickListener{
            //Do nothing
            Log.e("Adapter", "inside")
            viewModel.navigate(it)

        })

        binding.recipesRecyclerView.adapter = adapter

        //adapter.submitList(mockDataList)

        //OBSERVERS
        viewModel.recipeList.observe(viewLifecycleOwner,Observer{

            if(it.size > 0){
                binding.recipeEmptyMessage.setVisibility(View.GONE)
                adapter.submitList(it)
            }else{
                binding.recipeEmptyMessage.setVisibility(View.VISIBLE)
            }

        })

        viewModel.navigationTrigger.observe(viewLifecycleOwner,Observer{
            if(it != null){
                Log.e("NavigationTrigger","inside")
                var fragment = RecipeDetailFragment()
                var communicator = requireActivity() as Communicator
                viewModel.doneNavigating()
                communicator.transferData(it)

            }

        })



        return binding.root
    }

    /**
     * @method replaceFragment
     * @description swaps fragments within the NavHost
     * @param {Fragment} the fragment to change to
     */
    fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            parentFragmentManager
                .beginTransaction().replace(R.id.navHostFragment,fragment).commit()
            activity?.setTitle("Recipe")

        }
    }


}

