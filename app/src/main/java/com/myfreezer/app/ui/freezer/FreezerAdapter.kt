package com.myfreezer.app.ui.freezer


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.myfreezer.app.R
import com.myfreezer.app.models.FreezerItem

/**
 * @class FreezerAdapter
 * @description: The adapter for the freezer item recycler view
 *
 */

class FreezerAdapter(val onClickListener: OnClickListener): ListAdapter<FreezerItem,FreezerAdapter.FreezerViewHolder>(FreezerDiffCallback()) {

    /**
     * @description: The view holder for freezer_list_item
     */
    class FreezerViewHolder(viewItem:View): RecyclerView.ViewHolder(viewItem){

        //getting the individual views from freezer_list_item
        val textViewItemName: TextView = viewItem.findViewById(R.id.freezerListItemName)
        val textViewItemQuantity: TextView = viewItem.findViewById(R.id.freezerListItemQuantity)
        val textViewItemUnit: TextView = viewItem.findViewById(R.id.freezerListItemUnit)

        /**
         * @method bind
         * @description binds freezerItem content to individual freezer_list_item
         * @param {FreezerViewHolder} holder: The view holder that holds the freezer_list_item
         * @param {FreezerItem} item: The content to be bound
         */
        fun bind(holder:FreezerViewHolder, item:FreezerItem){

            holder.textViewItemName.text = item.name
            holder.textViewItemQuantity.text = item.quantity.toString()
            holder.textViewItemUnit.text = item.unit
        }


        companion object{
            /**
             * @method from
             * @description: Inflates the freezer_list_item view
             * @param {ViewGroup} parent: The parent context
             * @return {Object} FreezerViewHolder
             */
            fun from(parent: ViewGroup):FreezerViewHolder{
                val view:View = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.freezer_list_item,
                        parent,
                        false
                    )

                return FreezerViewHolder(view)

            }
        }

    }

    /**
     * @method onCreateViwHolder
     * @description lifecycle method that inflates the view on create.
     * @param {ViewGroup} parent: The parent view for context
     * @param {Int} viewType: The type of view as an integer
     * @return FreezerViewHolder - inflated view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreezerViewHolder {
        return FreezerViewHolder.from(parent)
    }

    /**
     * @method onBindViewHolder
     * @description lifecycle method that handles the binding of inflated view
     * @param {FreezerViewHolder} holder: The inflated view
     * @param {Int} position: the item position in list
     */
    override fun onBindViewHolder(holder:FreezerViewHolder, position: Int){
        val item = getItem(position)
        holder.bind(holder,item)
        holder.itemView.setOnLongClickListener{
             var x = it.findViewById<FreezerListItem>(R.id.freezerListItem)
            //it.setBackgroundColor(Color.parseColor("#9DB2B4"))
            onClickListener.onClick(item)
            return@setOnLongClickListener true
        }
    }

    /**
     * @class OnClickListener
     * @description Provides an instatiation of onClick to pass click listener to individual list items
     */
    class OnClickListener(val clickListener:(freezerItem:FreezerItem)-> Unit){
        fun onClick(freezerItem:FreezerItem) =  clickListener(freezerItem)

    }
}

/**
 * @class FreezerDiffCallBack
 * @description Provides an implementation of DiffUtill which performs diffing
 * for individual list items, this ensures that individual list items are updated when they change
 * and that only those that change are updated.
 */
class FreezerDiffCallback: DiffUtil.ItemCallback<FreezerItem>(){

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