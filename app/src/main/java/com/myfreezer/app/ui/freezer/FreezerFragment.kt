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
import androidx.lifecycle.viewModelScope
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.shared.freezerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        //INITIALIZING VARIABLES

        //Layout used for add item popup modal
        val addItemLayout = View.inflate(context,R.layout.add_item,null)

        //The add item popup dialogue
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



        //When the add button is clicked
        binding.floatingActionButton.setOnClickListener{
            //open the add item popup modal
            displayAddItemModal(addDialog,addItemLayout,viewModel)
        }


        return binding.root

    }

    /**
     * @method displayAddItemModal
     * @discription: Opens and displays the add new item popup modal
     * @param {AlertDialog} dialog: The alert dialog
     * @param {View} itemLayout: The custom layout for the add new item popup modal
     * @param {FreezerViewModel} viewModel: The viewModel used for accessing viewModel methods
     */
    private fun displayAddItemModal(dialog:AlertDialog, itemLayout:View,viewModel:FreezerViewModel){

        val cancelButton: Button = itemLayout.findViewById(R.id.addItemCancelButton)
        val addButton: Button = itemLayout.findViewById(R.id.addItemAddButton)



        cancelButton.setOnClickListener{
            dialog.dismiss()
        }

        addButton.setOnClickListener{

            //add
            addItem(itemLayout,viewModel)

            //dismiss modal
            dialog.dismiss()
        }

        dialog.show()

    }

    /**
     * @method addItem
     * @description Adds new freezer item to database and clears the textfields
     * @param {View} itemLayout: The custom layout for the popup modal
     * @param {FreezerViewModel} viewModel: the viewModel
     */
    fun addItem(itemLayout:View,viewModel:FreezerViewModel){
        val itemNameView: EditText = itemLayout.findViewById(R.id.addItemNameField)
        val itemQuantityView: EditText = itemLayout.findViewById(R.id.addItemQuantityField)
        val itemUnitView: EditText = itemLayout.findViewById(R.id.addItemUnitField)
        val itemMinimumView: EditText = itemLayout.findViewById(R.id.addItemMinimumField)

        //get values from text fields
        var newFreezerItem = FreezerItem(itemNameView.text.toString(),itemQuantityView.text.toString().toInt(),itemUnitView.text.toString(),itemMinimumView.text.toString().toInt())

        //Create new freezerListItem
        viewModel.addItem(newFreezerItem)

        //clear textfields
        itemNameView.setText("")
        itemQuantityView.setText("")
        itemUnitView.setText("")
        itemMinimumView.setText("")
    }



}