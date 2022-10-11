package com.myfreezer.app.ui.recipes.recipedetail.ingredients

import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.myfreezer.app.R
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.models.IngredientItem
import com.myfreezer.app.shared.constants.Constants

class RecipeIngredientsAdapter(var onClickListener:OnClickListener): ListAdapter<IngredientItem,RecipeIngredientsAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    /**
     * @class IngredientViewHolder
     * @description: The view holder for ingredient_list_item
     * @param {View} viewItem - The layout or view of the list item
     */
    class IngredientViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){

        var titleView: TextView = itemView.findViewById(R.id.ingredientTitle)
        var amountView: TextView = itemView.findViewById(R.id.ingredientQuantity)
        var unitView:TextView = itemView.findViewById(R.id.ingredientUnit)


        /**
         * @method bind
         * @description binds ingredientItem content to individual ingredient_list_item
         * @param {IngredientViewHolder} holder: The view holder that holds the ingredient_list_item
         * @param {IngredientItem} item: The content to be bound
         */
        fun bind(holder:IngredientViewHolder,item: IngredientItem){





            var amountSpannable = SpannableString(item.amount.toString())
            var unitSpannable = SpannableString(item.unit.toString())



            holder.titleView.text = item.name
            holder.amountView.text = amountSpannable.toString()
            holder.unitView.text = unitSpannable.toString()

        }

        companion object{
            /**
             * @method from
             * @description: Inflates the ingredient_list_item view
             * @param {ViewGroup} parent: The parent context
             * @return {IngredientViewHolder} IngredientViewHolder - an instance of inflated viewHolder
             */
            fun from(parent: ViewGroup):IngredientViewHolder{

                var view:View = LayoutInflater.from(parent.context).inflate(
                    R.layout.ingredient_list_item,
                    parent,
                    false
                )

                return IngredientViewHolder(view)

            }
        }

    }

    /**
     * @method onCreateViewHolder
     * @description lifecycle method that inflates the view on create.
     * @param {ViewGroup} parent: The parent view for context
     * @param {Int} viewType: The type of view as an integer
     * @return IngredientViewHolder - inflated view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }

    /**
     * @method onBindViewHolder
     * @description lifecycle method that handles the binding of inflated view
     * @param {IngredientViewHolder} holder: The inflated view
     * @param {Int} position: the item position in list
     */
    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(holder,item)
    }



    class OnClickListener(var clickListener:(ingredient:IngredientItem)-> Unit){
        fun onClick(ingredientItem:IngredientItem) = clickListener(ingredientItem)
    }
}

/**
 * @class IngredientDiffCallBack
 * @description Provides an implementation of DiffUtill which performs diffing
 * for individual list items, this ensures that individual list items are updated when they change
 * and that only those that change are updated.
 */
class IngredientDiffCallback: DiffUtil.ItemCallback<IngredientItem>(){

    /**
     * @method: areItemsTheSame
     * @description compares items to see if they are the same type
     * @param {IngredientItem} oldItem
     * @param {IngredientItem} newItem
     * @return {Boolean} true if the items are the same type
     */
    override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
        return oldItem == newItem
    }

    /**
     * @method: areContentsTheSame
     * @description compares contents of items to see if they are the same.
     * @param {IngredientItem} oldItem
     * @param {IngredientItem} newItem
     * @return {Boolean} true if the items are the same
     */
    override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {

        //TODO: FIX are contentTheSame
        //Currently it always returns true because oldItem gets updated before it is compared to newItem
        return false

    }
}