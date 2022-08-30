package com.myfreezer.app.ui.freezer

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.models.FreezerItem

/**
 * @class FreezerFragment
 * @description Contains the implementaiton of the FreezerFragment
 */
class FreezerFragment: Fragment() {

    lateinit var actionMode:ActionMode

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
        val addItemLayout = View.inflate(context,R.layout.add_freezer_item,null)
        val deleteConfirmationLayout = View.inflate(context, R.layout.delete_confirmation,null)

        //The add item popup dialogue
        val addDialogBuilder = AlertDialog.Builder(context)
        addDialogBuilder.setView(addItemLayout)
        addDialogBuilder.setCancelable(true)
        val addDialog = addDialogBuilder.create()

        //The deleteConfirmation popup dialogue
        val deleteConfirmation = AlertDialog.Builder(context)
        deleteConfirmation.setView(deleteConfirmationLayout)
        deleteConfirmation.setCancelable(false)
        val deleteDialog = deleteConfirmation.create()

        //VIEWMODEL

        //initialize viewModel
        val application = requireNotNull(this.activity).application
        val viewModelFactory = FreezerViewModelFactory(application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(FreezerViewModel::class.java)

        //ADAPTER

        //get adapter
        val adapter = FreezerAdapter(FreezerAdapter.OnClickListener{
            //TODO:Add the navigation observer

            displayFreezerListItemContextMenu(deleteDialog, viewModel,it,deleteConfirmationLayout)
        })

        //set adapter
        binding.freezerRecyclerView.adapter = adapter

        //OBSERVERS

        //When the list updates
        viewModel.freezerItemList.observe(viewLifecycleOwner, Observer{
            //resubmit the new list to the adapter for display
            if(it.size == 0){

                binding.freezerEmptyMessage.setVisibility(View.VISIBLE)
            }else{
                binding.freezerEmptyMessage.setVisibility(View.GONE)
            }
            adapter.submitList(it)

        })



        //When the add button is clicked
        binding.fabAddItem.setOnClickListener{
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

    /**
     * @method displayFreezerListItemContextMenu
     * @description Displays a context Menu with options to delete or edit item, when item is long pressed
     * @param {FreezerVieModel} viewModel: the freezer viewModel which has the delete and edit methods
     * @param {FreezerItem} freezerItem: the Item that has been selected
     * @return {Boolean} true
     */
    fun displayFreezerListItemContextMenu(dialog:AlertDialog,viewModel:FreezerViewModel,freezerItem:FreezerItem,itemLayout:View):Boolean{


        //Create the actionMode for context menu
        val fragmentActivity = requireActivity()
        actionMode = fragmentActivity.startActionMode(actionModeCallbackWrapper(dialog,viewModel,freezerItem,itemLayout))!!

        //Checks whether action mode allready exists,
        //if it does it wont create another one
        if(actionMode !== null){
            return false;
        }

        return true;
    }

    /**
     * @method: actionModeCallbackWrapper
     * @description: a wrapper function that wraps the ActionMode Callback function
     * The wrapper function is used to pass variables to items inside of the
     * actionItemClicked methods
     * @param {FreezerViewModel} viewModel: The viewModel that contains the desired methods
     * @param {FreezerItem} freezerItem: The item that was selected
     * @return: {ActionMode.Callback} The callback function for the actionMode
     */
    fun actionModeCallbackWrapper(dialog:AlertDialog,viewModel:FreezerViewModel,freezerItem:FreezerItem,itemLayout:View):ActionMode.Callback {


        return object: ActionMode.Callback {

            /**
             * @method: onCreateActionMode
             * @description: creates the context Menu
             * @param {ActionMode?} mode: Provide alternative mode of normal UI like a contextual action.
             * @param {Menu?} menu: The menu that needs to be inflated for the context menu
             * @return {Boolean}
             */
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //Inflate the menu with options that are displayed when context menu is shown
                mode?.getMenuInflater()!!.inflate(R.menu.freezer_context_menu,menu)

                //Set title for context menu
                mode.setTitle("Options");

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item?.getItemId()){
                    R.id.freezerContextMenuDelete -> {
                        displayDeleteConfirmationDialog(dialog,viewModel,freezerItem,itemLayout)
                        actionMode.finish()
                    }
                    else -> {
                        //do nothing

                    }

                }
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                //TODO("Not yet implemented")

            }

        }
    }



    /**
     * @method: displayDeleteConfirmationDialog
     * @description: prompts the user to either cancel or confirm a delete action
     * @param {}
     * dialog:AlertDialog, itemLayout:View,viewModel:FreezerViewModel
     */
    private fun displayDeleteConfirmationDialog(dialog:AlertDialog,viewModel:FreezerViewModel,item:FreezerItem,itemLayout:View){


        val cancelButton: Button = itemLayout.findViewById(R.id.deleteConfirmationCancelButton)
        val confirmButton: Button = itemLayout.findViewById(R.id.deleteConfirmationConfirmButton)



        cancelButton.setOnClickListener{
            dialog.dismiss()
        }

        confirmButton.setOnClickListener{

            //add
            viewModel.deleteFreezerItem(item)
            Toast.makeText(context, "Item deleted",Toast.LENGTH_LONG).show()

            //dismiss modal
            dialog.dismiss()
        }
//
//        dialog.show()

//        val builder = AlertDialog.Builder(context)
//        builder.setTitle(R.string.dialogue_delete_title)
//        builder.setMessage(R.string.dialogue_delete_body)
//        builder.setPositiveButton("Confirm"){dialogInterface,which ->
//
//            viewModel.deleteFreezerItem(item)
//            Toast.makeText(context, "Item deleted",Toast.LENGTH_LONG).show()
//        }
//        builder.setNegativeButton("Cancel"){dialogueInterface,which ->
//
//        }
//        val dialog = builder.create()
        dialog.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        actionMode.finish()
    }



}