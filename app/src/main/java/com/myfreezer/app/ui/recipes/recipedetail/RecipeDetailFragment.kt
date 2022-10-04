package com.myfreezer.app.ui.recipes.recipedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeDetailBinding
import com.myfreezer.app.models.RecipeItem

/**
 * @class RecipeDetailFragment
 * @description Contains the implementation of the RecipeDetailFragment
 */
class RecipeDetailFragment: Fragment() {

    //Declare variables
    lateinit var tabLayout:TabLayout.TabView
    lateinit var tabOverview: TabItem
    lateinit var tabIngredients: TabItem
    lateinit var tabInstructions: TabItem
    lateinit var viewPager: ViewPager2

    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return {FragmentRecipeDetailBinding} binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Create Binding
        val binding: FragmentRecipeDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_detail,
            container,
            false
        )

        //Get RecipeItem
        val recipeItem: RecipeItem = arguments?.getParcelable("RecipeItem")!!


        //set title in action bar
        val activity = requireActivity()
        activity.setTitle("Recipe")

        //Create and set adapter
        val adapter = RecipeDetailsPagerAdapter(activity.supportFragmentManager,lifecycle,recipeItem)
        binding.recipeViewPager.adapter = adapter

        //Setup Tabs
        TabLayoutMediator(binding.recipeTabLayout,binding.recipeViewPager){tab,position->
            when(position){
                0-> tab.text = "OVERVIEW"
                1-> tab.text = "INGREDIENTS"
                2-> tab.text = "INSTRUCTIONS"
                else -> {
                    //do nothing
                }
            }
        }.attach()


        //return view
        return binding.root

    }

}