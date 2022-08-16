package com.myfreezer.app.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFavouriteBinding
import com.myfreezer.app.databinding.FragmentFreezerBinding

class FavouriteFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Databinding
        val binding: FragmentFavouriteBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favourite,
            container,
            false
        )

        return binding.root
    }
}