package com.myfreezer.app.ui.freezer

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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

    //ActionMode used for appbar context menu
    lateinit var actionMode:ActionMode

    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return FragmentFreezerBinding
     */
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


        //SETUP AND INITIALISATION



        //Layout used for popup dialogs
        val addItemLayout = View.inflate(context,R.layout.add_freezer_item,null)
        val deleteConfirmationLayout = View.inflate(context, R.layout.delete_confirmation,null)
        val editItemLayout = View.inflate(context, R.layout.edit_freezer_item,null)

        //The add item popup dialogue
        val addDialogBuilder = AlertDialog.Builder(context)
        addDialogBuilder.setView(addItemLayout)
        addDialogBuilder.setCancelable(true)
        val addDialog = addDialogBuilder.create()

        //The edit item popup dialogue
        val editDialogBuilder = AlertDialog.Builder(context)
        addDialogBuilder.setView(editItemLayout)
        addDialogBuilder.setCancelable(true)
        val editDialog = addDialogBuilder.create()

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



            //When list item is clicked, display a context menu in the appbar
            //TODO:unscramble the argument order
            displayFreezerListItemContextMenu(
                viewModel,
                it,
                deleteDialog,
                deleteConfirmationLayout,
                editDialog,
                editItemLayout
            )
        },viewModel)

        //set adapter
        binding.freezerRecyclerView.adapter = adapter

        //OBSERVERS

        //Updates display when the freezerItemlist is updated
        viewModel.freezerItemList.observe(viewLifecycleOwner, Observer{

            //if no items in the list
            if(it.size == 0){
                //Then display the freezer background message
                //Informing user that the freezer is empty
                binding.freezerEmptyMessage.setVisibility(View.VISIBLE)
            }else{
                //Remove the background message so that items can be displayed
                binding.freezerEmptyMessage.setVisibility(View.GONE)
            }
            //resubmit the new list to the adapter for display
            adapter.submitList(it)
        })

        //FAB

        //When the add button is clicked
        binding.fabAddItem.setOnClickListener{
            //open the add item popup dialog
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

        //Get the buttons within the custom layout
        val cancelButton: Button = itemLayout.findViewById(R.id.addItemCancelButton)
        val addButton: Button = itemLayout.findViewById(R.id.addItemAddButton)

        //When cancel button is clicked
        cancelButton.setOnClickListener{
            //Dismiss the dialogue without adding anything to the list
            dialog.dismiss()
        }

        //When the add button is clicked
        addButton.setOnClickListener{

            //add the new item to database and display in list
            addItem(itemLayout,viewModel)

            //dismiss modal
            dialog.dismiss()
        }

        //Display the dialog
        dialog.show()
    }

    //TODO:
    /**
     * @method addItem
     * @description Adds new freezer item to database and clears the textfields
     * @param {View} itemLayout: The custom layout for the popup modal
     * @param {FreezerViewModel} viewModel: the viewModel
     */
    fun addItem(itemLayout:View,viewModel:FreezerViewModel){
        //Get all the EditText fields
        val itemNameView: EditText = itemLayout.findViewById(R.id.addItemNameField)
        val itemQuantityView: EditText = itemLayout.findViewById(R.id.addItemQuantityField)
        val itemUnitView: EditText = itemLayout.findViewById(R.id.addItemUnitField)
        val itemMinimumView: EditText = itemLayout.findViewById(R.id.addItemMinimumField)

        //get values from text fields
        //and create a new FreezerItem
        var newFreezerItem = FreezerItem(
            itemNameView.text.toString(),
            itemQuantityView.text.toString().toInt(),
            itemUnitView.text.toString(),
            itemMinimumView.text.toString().toInt()
        )

        //Add Item to database and disply in list
        viewModel.addItem(newFreezerItem)

        //clear text fields
        itemNameView.setText("")
        itemQuantityView.setText("")
        itemUnitView.setText("")
        itemMinimumView.setText("")
    }

    fun inputValidation(){

    }

    /**
     * @method displayFreezerListItemContextMenu
     * @description Displays a context Menu with options to delete or edit item, when item is long pressed
     * @param {FreezerVieModel} viewModel: the freezer viewModel which has the delete and edit methods
     * @param {FreezerItem} freezerItem: the Item that has been selected
     * @return {Boolean} true
     */
    fun displayFreezerListItemContextMenu(
        viewModel:FreezerViewModel,
        freezerItem:FreezerItem,
        addItemDialog:AlertDialog,
        addItemLayout:View,
        editItemDialog:AlertDialog,
        editItemLayout:View
    ):Boolean {
        //TODO: refactor the action mode check  so it makes sense


        //Create the actionMode for context menu
        val fragmentActivity = requireActivity()
        actionMode = fragmentActivity.startActionMode(actionModeCallbackWrapper(
            viewModel,
            freezerItem,
            addItemDialog,
            addItemLayout,
            editItemDialog,
            editItemLayout

        ))!!

        //Checks whether action mode already exists,
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
    fun actionModeCallbackWrapper(
        viewModel:FreezerViewModel,
        freezerItem:FreezerItem,
        addItemDialog:AlertDialog,
        addItemLayout:View,
        editItemDialog:AlertDialog,
        editItemLayout: View
    ):ActionMode.Callback {


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

            /**
             *
             */
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            /**
             *
             */
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item?.getItemId()){
                    R.id.freezerContextMenuDelete -> {
                        displayDeleteConfirmationDialog(addItemDialog,viewModel,freezerItem,addItemLayout)
                        actionMode.finish()
                    }
                    R.id.freezerContextMenuEdit -> {
                        displayEditItemDialog(editItemDialog,viewModel,freezerItem,editItemLayout)
                    }
                    else -> {
                        //do nothing

                    }

                }
                return true
            }

            /**
             *
             */
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

        //Get buttons in custom layout
        val cancelButton: Button = itemLayout.findViewById(R.id.deleteConfirmationCancelButton)
        val confirmButton: Button = itemLayout.findViewById(R.id.deleteConfirmationConfirmButton)

        //When cancel button is clicked
        cancelButton.setOnClickListener{
            //Dismiss the dialog without deleting item
            dialog.dismiss()
        }

        //When confirmButton is clicked
        confirmButton.setOnClickListener{

            //delete the item from database and remove from list
            viewModel.deleteFreezerItem(item)
            Toast.makeText(context, "Item deleted",Toast.LENGTH_LONG).show()

            //dismiss modal
            dialog.dismiss()
        }

        //Display the dialog
        dialog.show()

    }

    /**
     * @method: displayDeleteConfirmationDialog
     * @description: prompts the user to either cancel or confirm a delete action
     * @param {}
     * dialog:AlertDialog, itemLayout:View,viewModel:FreezerViewModel
     */
    private fun displayEditItemDialog(dialog:AlertDialog,viewModel:FreezerViewModel,item:FreezerItem,itemLayout:View){

        //Get buttons in custom layout
        val cancelButton: Button = itemLayout.findViewById(R.id.editItemCancelButton)
        val updateButton: Button = itemLayout.findViewById(R.id.editItemUpdateButton)

        //Get text fields
        val nameField:EditText = itemLayout.findViewById(R.id.editItemNameField)
        val quantityField:EditText = itemLayout.findViewById(R.id.editItemQuantityField)
        val unitField:EditText = itemLayout.findViewById(R.id.editItemUnitField)
        val minimumField:EditText = itemLayout.findViewById(R.id.editItemMinimumField)

        //Get PreviousId
        val previousId = item.name


        //Fill out fields with existng values
        nameField.setText(item.name)
        quantityField.setText(item.quantity.toString())
        unitField.setText(item.unit)
        minimumField.setText(item.minimum.toString())


        //When cancel button is clicked
        cancelButton.setOnClickListener{
            //Dismiss the dialog without deleting item
            dialog.dismiss()
        }

        //When updateButton is clicked
        updateButton.setOnClickListener{

            //Get text field values
            var nameValue = nameField.text.toString()
            var quantityValue = quantityField.text.toString().toInt()
            var unitValue = unitField.text.toString()
            var minimumValue = minimumField.text.toString().toInt()




            //Update existing freezer Item with new values
            item.name = nameValue
            item.quantity = quantityValue
            item.unit = unitValue
            item.minimum = minimumValue

            //Update database
            viewModel.updateFreezerItem(previousId,item)

            //close context menu
            actionMode.finish()

            //Notify User of update
            Toast.makeText(context, "Item updated",Toast.LENGTH_LONG).show()

            //dismiss modal
            dialog.dismiss()
        }

        //Display the dialog
        dialog.show()



    }

    /**
     *
     */
    override fun onDestroy() {
        super.onDestroy()

        //Close the appbar context menu when navigating away from the fragment
        if(this::actionMode.isInitialized){
            actionMode.finish()
        }

    }



}