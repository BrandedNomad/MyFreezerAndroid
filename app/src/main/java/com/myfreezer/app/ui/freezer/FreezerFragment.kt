package com.myfreezer.app.ui.freezer

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentFavouriteBinding.inflate
import com.myfreezer.app.databinding.FragmentFreezerBinding
import com.myfreezer.app.models.FreezerItem
import org.w3c.dom.Text
import java.lang.reflect.Array.get


/**
 * @class FreezerFragment
 * @description Contains the implementaiton of the FreezerFragment
 */
class FreezerFragment: Fragment(), MenuProvider {

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

    //Menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this,viewLifecycleOwner)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.freezer_filter_menu,menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        //do nothing
        return true
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
        val cancelButton: Button = itemLayout.findViewById(R.id.dialogItemCancelButton)
        val addButton: MaterialButton = itemLayout.findViewById(R.id.dialogItemSubmitButton)
        addButton.setEnabled(true)

        val nameField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemNameField)
        val quantityField:TextInputLayout= itemLayout.findViewById(R.id.dialogItemQuantityField)
        val unitField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemUnitField)
        val minimumField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemMinimumField)



        isFormComplete(itemLayout)

        //When cancel button is clicked
        cancelButton.setOnClickListener{
            //Dismiss the dialogue without adding anything to the list
            dialog.dismiss()
        }

        //When the add button is clicked
        addButton.setOnClickListener{


            //If all fields have been filled out and none is empty
            if(nameField.editText!!.text.toString() != "" && quantityField.editText!!.text.toString() != "" && unitField.editText!!.text.toString() != "" && minimumField.editText!!.text.toString() != ""){
                //add the new item to database and display in list
                addItem(itemLayout,viewModel)

                //dismiss modal
                dialog.dismiss()
            } else {
                //TODO:Refactor material edit box color
                if(nameField.editText!!.text.toString() == "") {
                    nameField.error = "Give your item a name!"
                }else{
                    nameField.error = null
                }
                if(quantityField.editText!!.text.toString() == "") {
                    quantityField.error = "How many/much?"
                }else{
                    quantityField.error = null
                }
                if(unitField.editText!!.text.toString() == "") {
                    unitField.error = "Kg? Pcs?"
                }else{
                    unitField.error = null
                }
                if(minimumField.editText!!.text.toString() == "") {
                    minimumField.error="warn when lower than?"
                }else{
                    minimumField.error = null
                }
            }



        }

        //Display the dialog
        dialog.show()
    }

    /**
     * @method isFormComplete
     * @description: Checks whether all fields are complete and Enables or disables submit button
     * @param {View} itemLayout:  The layout of item dialog to check
     */
    private fun isFormComplete(
        itemLayout:View
    ){

        //Get Views from layout
        val submitButton:MaterialButton = itemLayout.findViewById(R.id.dialogItemSubmitButton)
        val nameField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemNameField)
        val quantityField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemQuantityField)
        val unitField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemUnitField)
        val minimumField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemMinimumField)

        //Set Live data variable which will be used to check the state of form
        var isFilledCheck = MutableLiveData<Boolean>()
        isFilledCheck.value = false

        //individual flags used to inform the stateChecker of the state of each individual field
        var name = nameField.editText!!.text.toString() !=""
        var quantity = quantityField.editText!!.text.toString() != ""
        var unit = unitField.editText!!.text.toString() != ""
        var minimum = minimumField.editText!!.text.toString() != ""

        //Constantly checks the state of the form
        //This check is triggered each time the user interacts with one of the fields
        isFilledCheck.observe(viewLifecycleOwner,Observer{
            //If all fields are completed
            if(name && quantity && unit && minimum){
                //Enable the submit button
                //submitButton.setEnabled(true)
                submitButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                submitButton.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary)))
            }else{
                //If not then disable the submit button
                //submitButton.setEnabled(false)
                submitButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.disabled))
                submitButton.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.disabled)))
            }
        })

        //When the user types into the name field
        nameField.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(inputText.toString() != ""){
                name = true
                nameField.error = null
                isFilledCheck.value = !isFilledCheck.value!!

            }else{
                name = false
                isFilledCheck.value = !isFilledCheck.value!!
            }
        }

        //When the user types into the quantity field
        quantityField.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(inputText.toString() != ""){
                quantity = true
                quantityField.error = null
                isFilledCheck.value = !isFilledCheck.value!!

            }else{
                quantity = false
                isFilledCheck.value = !isFilledCheck.value!!
            }
        }

        //When the user types into the unit field
        unitField.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(inputText.toString() != ""){
                unit = true
                unitField.error = null
                isFilledCheck.value = !isFilledCheck.value!!

            }else{
                unit = false
                isFilledCheck.value = !isFilledCheck.value!!
            }
        }

        //When the user types into the minimum field
        minimumField.editText?.doOnTextChanged { inputText, _, _, _ ->
            if(inputText.toString() != ""){
                minimum = true
                minimumField.error = null
                isFilledCheck.value = !isFilledCheck.value!!

            }else{
                minimum = false
                isFilledCheck.value = !isFilledCheck.value!!
            }
        }
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
        val itemNameView: TextInputLayout = itemLayout.findViewById(R.id.dialogItemNameField)
        val itemQuantityView: TextInputLayout = itemLayout.findViewById(R.id.dialogItemQuantityField)
        val itemUnitView: TextInputLayout = itemLayout.findViewById(R.id.dialogItemUnitField)
        val itemMinimumView: TextInputLayout = itemLayout.findViewById(R.id.dialogItemMinimumField)

        //get values from text fields
        //and create a new FreezerItem
        var newFreezerItem = FreezerItem(
            itemNameView.editText!!.text.toString(),
            itemQuantityView.editText!!.text.toString().toInt(),
            itemUnitView.editText!!.text.toString(),
            itemMinimumView.editText!!.text.toString().toInt()
        )

        //Add Item to database and disply in list
        viewModel.addItem(newFreezerItem)

        //clear text fields
        itemNameView.editText!!.text.clear()
        itemQuantityView.editText!!.text.clear()
        itemUnitView.editText!!.text.clear()
        itemMinimumView.editText!!.text.clear()
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

                //inform the adapter that view model is open
                viewModel.setContextMenuOpen()

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
                        actionMode.finish()

                    }

                }
                return true
            }

            /**
             *
             */
            override fun onDestroyActionMode(mode: ActionMode?) {
                //TODO("Not yet implemented")
                //inform adapter the context menu is closed
                viewModel.setContextMenuClosed()

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
        val cancelButton: Button = itemLayout.findViewById(R.id.dialogItemCancelButton)
        val updateButton: Button = itemLayout.findViewById(R.id.dialogItemSubmitButton)

        //Get text fields
        val nameField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemNameField)
        val quantityField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemQuantityField)
        val unitField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemUnitField)
        val minimumField:TextInputLayout = itemLayout.findViewById(R.id.dialogItemMinimumField)

        //Get PreviousId
        val previousId = item.name


        //Fill out fields with existng values
        nameField.editText!!.setText(item.name)
        quantityField.editText!!.setText(item.quantity.toString())
        unitField.editText!!.setText(item.unit)
        minimumField.editText!!.setText(item.minimum.toString())

        isFormComplete(itemLayout)


        //When cancel button is clicked
        cancelButton.setOnClickListener{
            //Dismiss the dialog without deleting item
            dialog.dismiss()
        }

        //When updateButton is clicked
        updateButton.setOnClickListener{
            //If all fields are filled out and none is empty
            if(
                nameField.editText!!.text.toString() != ""
                && quantityField.editText!!.text.toString() != ""
                && unitField.editText!!.text.toString() != ""
                && minimumField.editText!!.text.toString() != ""
            ){
                //Get text field values
                var nameValue = nameField.editText!!.text.toString()
                var quantityValue = quantityField.editText!!.text.toString().toInt()
                var unitValue = unitField.editText!!.text.toString()
                var minimumValue = minimumField.editText!!.text.toString().toInt()

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
            }else{
                if(nameField.editText!!.text.toString() == "") {
                    nameField.error = "Give your item a name!"
                }else{
                    nameField.error = null
                }
                if(quantityField.editText!!.text.toString() == "") {
                    quantityField.error = "How many/much?"
                }else{
                    quantityField.error = null
                }
                if(unitField.editText!!.text.toString() == "") {
                    unitField.error = "Kg? Pcs?"
                }else{
                    unitField.error = null
                }
                if(minimumField.editText!!.text.toString() == "") {
                    minimumField.error="warn when lower than?"
                }else{
                    minimumField.error = null
                }
            }
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