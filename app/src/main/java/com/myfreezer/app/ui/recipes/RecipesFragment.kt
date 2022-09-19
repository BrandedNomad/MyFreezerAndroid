package com.myfreezer.app.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.databinding.FragmentRecipesBinding
import com.myfreezer.app.ui.freezer.FreezerViewModel
import com.myfreezer.app.ui.freezer.FreezerViewModelFactory

class RecipesFragment: Fragment() {

    //Declare Variables
    lateinit var viewModel:RecipesViewModel

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



        //LAYOUTS

        //VIEWMODEL

        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecipesViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RecipesViewModel::class.java)

        return binding.root
    }
}