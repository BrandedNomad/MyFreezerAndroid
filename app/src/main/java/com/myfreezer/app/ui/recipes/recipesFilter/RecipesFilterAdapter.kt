package com.myfreezer.app.ui.recipes.recipesFilter

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.viewModelScope

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myfreezer.app.R
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.ui.recipes.RecipesAdapter
import com.myfreezer.app.ui.recipes.RecipesViewModel
import kotlinx.coroutines.launch

class RecipesFilterAdapter(val onClickListener: RecipesFilterAdapter.OnClickListener,val addItem:(String)-> Unit,val removeItem:(String)->Unit): ListAdapter<FreezerItem, RecipesFilterAdapter.RecipesFilterViewHolder>(RecipesFilterDiffCallback()) {




    class RecipesFilterViewHolder(viewItem: View, val addItem:(String)->Unit,val removeItem:(String)->Unit): RecyclerView.ViewHolder(viewItem) {


        val textViewItemName: TextView = viewItem.findViewById(R.id.recipeFilterFreezerMenuItemText)
        val checkBox: CheckBox = viewItem.findViewById(R.id.recipeFilterFreezerMenuItemCheckbox)


        fun bind(holder: RecipesFilterViewHolder,item:FreezerItem){

            textViewItemName.text = item.name
            checkBox.setOnCheckedChangeListener{ buttonView,isChecked ->


                if(isChecked){
                    var nameToAdd = textViewItemName.text
                    addItem(nameToAdd.toString())

                    Log.e("checkbox-true", nameToAdd.toString())

                } else {
                    var nameToRemove = textViewItemName.text
                    removeItem(nameToRemove.toString())
                    Log.e("checkbox-false", nameToRemove.toString())
                }

            }



        }

        companion object{
            /**
             * @method from
             * @description: Inflates the recipe_list_item view
             * @param {ViewGroup} parent: The parent context
             * @return {RecipesViewHolder} recipeViewHolder
             */
            fun from(parent: ViewGroup,addItem:(String)-> Unit,removeItem:(String)->Unit): RecipesFilterAdapter.RecipesFilterViewHolder {
                //inflate view
                val view:View = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recipes_filter_list_item,
                        parent,
                        false
                    )

                return RecipesFilterAdapter.RecipesFilterViewHolder(view,addItem,removeItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesFilterViewHolder {
        return RecipesFilterViewHolder.from(parent,addItem,removeItem)
    }

    override fun onBindViewHolder(holder: RecipesFilterViewHolder, position: Int) {
        var item = getItem(position)
        holder.bind(holder,item)

    }



    /**
     * @class OnClickListener
     * @description Provides an instatiation of onClick to pass click listener to individual list items
     */
    class OnClickListener(val clickListener:(freezerItem: FreezerItem)-> Unit){
        fun onClick(freezerItem: FreezerItem) =  clickListener(freezerItem)

    }



}



/**
 * @class RecipesDiffCallBack
 * @description Provides an implementation of DiffUtill which performs diffing
 * for individual list items, this ensures that individual list items are updated when they change
 * and that only those that change are updated.
 */
class RecipesFilterDiffCallback(): DiffUtil.ItemCallback<FreezerItem>(){
    /**
     * @method: areItemsTheSame
     * @description compares items to see if they are the same type
     * @param {FreezerItem} oldItem
     * @param {FreezerItem} newItem
     * @return {Boolean} true if the items are the same type
     */
    override fun areItemsTheSame(oldItem: FreezerItem, newItem: FreezerItem): Boolean {
        return oldItem == newItem
    }

    /**
     * @method: areContentsTheSame
     * @description compares contents of items to see if they are the same.
     * @param {FreezerItem} oldItem
     * @param {FreezerItem} newItem
     * @return {Boolean} true if the items are the same
     */
    override fun areContentsTheSame(oldItem: FreezerItem, newItem: FreezerItem): Boolean {
        return oldItem.name == newItem.name
    }


}