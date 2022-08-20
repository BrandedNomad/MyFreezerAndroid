package com.myfreezer.app.ui.freezer

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.models.FreezerItem
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

        //Variables

        val addItemLayout = View.inflate(context,R.layout.add_item,null)


        val addDialogBuilder = AlertDialog.Builder(context)
        addDialogBuilder.setView(addItemLayout)
        addDialogBuilder.setCancelable(true)
        val addDialog = addDialogBuilder.create()

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




        binding.floatingActionButton.setOnClickListener{
            displayAddItemModal(addDialog,addItemLayout,viewModel)
        }


        return binding.root

    }

    private fun displayAddItemModal(dialog:AlertDialog, itemLayout:View,viewModel:FreezerViewModel){

        val cancelButton: Button = itemLayout.findViewById(R.id.addItemCancelButton)
        val addButton: Button = itemLayout.findViewById(R.id.addItemAddButton)
        val itemNameView: EditText = itemLayout.findViewById(R.id.addItemNameField)
        val itemQuantityView: EditText = itemLayout.findViewById(R.id.addItemQuantityField)
        val itemUnitView: EditText = itemLayout.findViewById(R.id.addItemUnitField)
        val itemMinimumView: EditText = itemLayout.findViewById(R.id.addItemMinimumField)


        cancelButton.setOnClickListener{
            dialog.dismiss()
        }

        addButton.setOnClickListener{

            //get values from text fields
            var newFreezerItem = FreezerItem(itemNameView.text.toString(),itemQuantityView.text.toString(),itemUnitView.text.toString())

            //Create new freezerListItem
            freezerList.add(newFreezerItem)

            //clear textfields
            itemNameView.setText("")
            itemQuantityView.setText("")
            itemUnitView.setText("")
            itemMinimumView.setText("")

            //dismiss modal
            dialog.dismiss()
        }

        dialog.show()

    }
}