package com.myfreezer.app.ui.recipes.recipedetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.ui.freezer.FreezerFragment

class RecipeDetailsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, recipeItem:RecipeItem): FragmentStateAdapter(fragmentManager,lifecycle) {

    var bundle = Bundle()
    var recipeOverviewFragment = RecipeOverviewFragment()
    var recipeIngredientsFragment = RecipeIngredientsFragment()
    var recipeInstructionsFragment = RecipeInstructionsFragment()
    init{
        bundle.putParcelable("RecipeItem",recipeItem)
        recipeOverviewFragment.arguments = bundle
        recipeIngredientsFragment.arguments = bundle
        recipeInstructionsFragment.arguments = bundle
    }




    override fun getItemCount(): Int {
        return 3

    }


    override fun createFragment(position: Int): Fragment {
        Log.e("Inside pageView Adapter",bundle.getParcelable<RecipeItem>("RecipeItem").toString())

        return when(position){
            0-> recipeOverviewFragment
            1-> recipeIngredientsFragment
            2-> recipeInstructionsFragment
            else -> recipeOverviewFragment
        }




    }
}