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


class RecipeDetailFragment: Fragment() {

    lateinit var tabLayout:TabLayout.TabView
    lateinit var tabOverview: TabItem
    lateinit var tabIngredients: TabItem
    lateinit var tabInstructions: TabItem
    lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentRecipeDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_detail,
            container,
            false
        )

        //Initialize
        var recipeItem: RecipeItem = arguments?.getParcelable("RecipeItem")!!
        Log.e("Detail -RecipeItem",recipeItem.toString())
        var activity = requireActivity()
        activity!!.setTitle("Recipe")

        //Get Layouts
        val adapter = RecipeDetailsPagerAdapter(activity?.supportFragmentManager!!,lifecycle,recipeItem)

        binding.recipeViewPager.adapter = adapter

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


        return binding.root

    }

}