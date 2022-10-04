package com.myfreezer.app.ui.recipes.recipedetail.overview

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipeInstructionsBinding
import com.myfreezer.app.databinding.FragmentRecipeOverviewBinding
import com.myfreezer.app.models.RecipeItem

/**
 * @class RecipeOverviewFragment
 * @description: contains the implementation of RecipeOverviewFragment which provides the overview or summary of a recipe
 */
class RecipeOverviewFragment: Fragment() {

    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return {FragmentRecipeOverviewBinding} binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Create databinding
        val binding: FragmentRecipeOverviewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_overview,
            container,
            false
        )

        //Get Recipe Item
        var recipeItem:RecipeItem? = arguments?.getParcelable("RecipeItem")

        //Bind Recipe data to view
        //Download and buffer image
        var requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.fake_recipe)
            .error(R.drawable.fake_recipe)
        Glide.with(binding.recipeOverviewImage.getContext()).load(recipeItem!!.image).apply(requestOptions).into(binding.recipeOverviewImage)

        //set title
        binding.recipeOverviewTitle.text = recipeItem!!.title

        //The following conditional statements sets the icons and text colors to green if the preference is met
        if(recipeItem.glutenFree){
            binding.preferenceGlutenIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.check))
            binding.preferenceGlutenIcon.setColorFilter(binding.preferenceGlutenIcon.context.getColor(R.color.icon_green))
            binding.preferenceGlutenText.setTextColor(binding.preferenceGlutenText.context.getColor(R.color.icon_green))
        }

        if(recipeItem.dairyFree){
            binding.preferenceDairyIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.check))
            binding.preferenceDairyIcon.setColorFilter(binding.preferenceDairyIcon.context.getColor(R.color.icon_green))
            binding.preferenceDairyText.setTextColor(binding.preferenceDairyText.context.getColor(R.color.icon_green))
        }

        if(recipeItem.vegan){
            binding.preferenceVeganIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.check))
            binding.preferenceVeganIcon.setColorFilter(binding.preferenceVeganIcon.context.getColor(R.color.icon_green))
            binding.preferenceVeganText.setTextColor(binding.preferenceVeganText.context.getColor(R.color.icon_green))
        }

        if(recipeItem.healthy){
            binding.preferenceHealthyIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.check))
            binding.preferenceHealthyIcon.setColorFilter(binding.preferenceHealthyIcon.context.getColor(R.color.icon_green))
            binding.preferenceHealthyText.setTextColor(binding.preferenceHealthyText.context.getColor(R.color.icon_green))
        }

        if(recipeItem.vegetarian){
            binding.preferenceVegetarianIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.check))
            binding.preferenceVegetarianIcon.setColorFilter(binding.preferenceVegetarianIcon.context.getColor(R.color.icon_green))
            binding.preferenceVegetarianText.setTextColor(binding.preferenceVegetarianText.context.getColor(R.color.icon_green))
        }

        if(recipeItem.fodmap){
            binding.preferenceFodmapIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.check))
            binding.preferenceFodmapIcon.setColorFilter(binding.preferenceFodmapIcon.context.getColor(R.color.icon_green))
            binding.preferenceFodmapText.setTextColor(binding.preferenceFodmapText.context.getColor(R.color.icon_green))
        }

        //Sets the description or summary which describes the recipe
        binding.recipeOverviewDescription.text = recipeItem!!.description

        //returns the view
        return binding.root
    }
}