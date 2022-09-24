package com.myfreezer.app.ui.recipes.recipedetail

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

class RecipeOverviewFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentRecipeOverviewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_overview,
            container,
            false
        )

        //Initialize variables
        var recipeItem:RecipeItem? = arguments?.getParcelable("RecipeItem")

        //GET LAYOUTS


        var requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.fake_recipe)
            .error(R.drawable.fake_recipe)

        Glide.with(binding.recipeOverviewImage.getContext()).load(recipeItem!!.image).apply(requestOptions).into(binding.recipeOverviewImage)

        binding.recipeOverviewTitle.text = recipeItem!!.title

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

        binding.recipeOverviewDescription.text = recipeItem!!.description


        return binding.root
    }
}