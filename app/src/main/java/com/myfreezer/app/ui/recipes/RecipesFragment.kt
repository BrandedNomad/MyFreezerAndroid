package com.myfreezer.app.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.databinding.FragmentRecipesBinding

class RecipesFragment: Fragment() {

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

        return binding.root
    }
}