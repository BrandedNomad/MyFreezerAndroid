package com.myfreezer.app.ui.recipes.recipesFilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeFilterBinding
import com.myfreezer.app.ui.recipes.RecipesViewModel

class RecipesFilterFragment(val viewModel: RecipesViewModel): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding:FragmentRecipeFilterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_filter,
            container,
            false
        )

        binding.recipeFilterCloseButton.setOnClickListener{
            var transaction = requireActivity().supportFragmentManager.popBackStack("Filter",1)

        }

        val addItem = viewModel::addToFilter
        val removeItem = viewModel::removeFromFilter
        val getFilter = viewModel::getFilter

        val adapter = RecipesFilterAdapter(RecipesFilterAdapter.OnClickListener{

        },getFilter,addItem,removeItem)



        binding.recipeFilterFreezerMenuRecyclerView.adapter = adapter

        viewModel.getFreezerItemList().observe(viewLifecycleOwner, Observer{
            Log.e("Fragment",it.toString())
            adapter.submitList(it)
        })

        viewModel.recipesFilter.observe(viewLifecycleOwner,Observer{
            Log.e("The MutableList",it.toString())

        })

        //Chips

//        binding.recipeFilterPreferenceChipVegan.setOnCheckedChangeListener { chip, isChecked ->
//            if(isChecked){
//                viewModel.addToPreferences("vegan")
//            }else{
//                viewModel.removeFromPreferences("vegan")
//            }
//            Log.e("veganOnClick",viewModel.getPreferences().toString())
//        }
//
//        binding.recipeFilterPreferenceChipVegetarian.setOnCheckedChangeListener { chip, isChecked ->
//            if(isChecked){
//                viewModel.addToPreferences("vegetarian")
//            }else{
//                viewModel.removeFromPreferences("vegetarian")
//            }
//            Log.e("vegetarianOnClick",viewModel.getPreferences().toString())
//        }
//
//        binding.recipeFilterPreferenceChipGlutenFree.setOnCheckedChangeListener { chip, isChecked ->
//            if(isChecked){
//                viewModel.addToPreferences("gluten free")
//            }else{
//                viewModel.removeFromPreferences("gluten free")
//            }
//            Log.e("glutenfreeOnClick",viewModel.getPreferences().toString())
//        }
//
//        binding.recipeFilterPreferenceChipDairyFree.setOnCheckedChangeListener { chip, isChecked ->
//            if(isChecked){
//                viewModel.addToPreferences("dairy free")
//            }else{
//                viewModel.removeFromPreferences("dairy free")
//            }
//            Log.e("dairyfreeOnClick",viewModel.getPreferences().toString())
//        }
//
//        binding.recipeFilterPreferenceChipHealthy.setOnCheckedChangeListener { chip, isChecked ->
//            if(isChecked){
//                viewModel.addToPreferences("healthy")
//            }else{
//                viewModel.removeFromPreferences("healthy")
//            }
//            Log.e("healthyOnClick",viewModel.getPreferences().toString())
//        }
//
//        binding.recipeFilterPreferenceChipFodmap.setOnCheckedChangeListener { chip, isChecked ->
//            if(isChecked){
//                viewModel.addToPreferences("fodmap")
//            }else{
//                viewModel.removeFromPreferences("fodmap")
//            }
//            Log.e("fodmapOnClick",viewModel.getPreferences().toString())
//        }


        //Log.e("filter",viewModel.getFreezerItems().toString())

        //adapter.submitList()

        return binding.root
    }


}