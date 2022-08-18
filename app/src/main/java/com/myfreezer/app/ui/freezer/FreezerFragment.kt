package com.myfreezer.app.ui.freezer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.shared.freezerList

/**
 * @class FreezerFragment
 * @description Contains the implementaiton of the FreezerFragment
 */
class FreezerFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //BINDING

        //create binding
        val binding: FragmentFreezerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_freezer,
            container,
            false
        )

        //set lifecycle owner
        binding.lifecycleOwner = this

        //VIEWMODEL

        //initialize viewModel
        val application = requireNotNull(this.activity).application
        val viewModelFactory = FreezerViewModelFactory(application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(FreezerViewModel::class.java)

        //ADAPTER

        //get adapter
        val adapter = FreezerAdapter(FreezerAdapter.OnClickListener{
            //TODO:Add the navigation observer
        })

        //set adapter
        binding.freezerReyclerView.adapter = adapter

        //OBSERVERS

        //When the list updates
        viewModel.freezerItemList.observe(viewLifecycleOwner, Observer{
            //resubmit the new list to the adapter for display
            adapter.submitList(it)
        })


        return binding.root

    }
}