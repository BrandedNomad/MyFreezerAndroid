package com.myfreezer.app.ui.recipes.recipedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.ui.recipes.recipedetail.ingredients.RecipeIngredientsFragment
import com.myfreezer.app.ui.recipes.recipedetail.instructions.RecipeInstructionsFragment
import com.myfreezer.app.ui.recipes.recipedetail.overview.RecipeOverviewFragment

/**
 * @class RecipeDetailsPagerAdapter
 * @description The viewPager adapter for the viewPager. Manages the fragments in the viewPager.
 * @param {FragmentManager} fragmentManager - The activity fragment manager
 * @param {LifeCycle} lifecycle - The lifecycle of the provider
 * @param {RecipeItem} recipeItem - The recipeItem to display
 */
class RecipeDetailsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, recipeItem:RecipeItem): FragmentStateAdapter(fragmentManager,lifecycle) {

    //Create bundle
    var bundle = Bundle()

    //Get fragments for viewPager
    var recipeOverviewFragment = RecipeOverviewFragment()
    var recipeIngredientsFragment = RecipeIngredientsFragment()
    var recipeInstructionsFragment = RecipeInstructionsFragment()

    //initialize variables
    init{
        //pass the recipeItem to the fragments of the viewPager
        bundle.putParcelable("RecipeItem",recipeItem)
        recipeOverviewFragment.arguments = bundle
        recipeIngredientsFragment.arguments = bundle
        recipeInstructionsFragment.arguments = bundle
    }


    /**
     * @method returnItemCount
     * @description returns the number of views in the view pager
     * @return {Int} integer
     */
    override fun getItemCount(): Int {
        return 3
    }


    /**
     * @method createFragment
     * @description creates and displays the appropriate fragment based on selected tab
     * @param {Int} position - the tab position
     */
    override fun createFragment(position: Int): Fragment {

        return when(position){
            0-> recipeOverviewFragment
            1-> recipeIngredientsFragment
            2-> recipeInstructionsFragment
            else -> recipeOverviewFragment
        }

    }
}