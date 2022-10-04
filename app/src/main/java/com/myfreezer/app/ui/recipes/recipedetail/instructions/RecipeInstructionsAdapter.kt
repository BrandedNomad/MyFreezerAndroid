package com.myfreezer.app.ui.recipes.recipedetail.instructions

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myfreezer.app.R
import com.myfreezer.app.models.IngredientItem
import com.myfreezer.app.models.InstructionItem

class RecipeInstructionsAdapter(onClickListener:OnClickListener):ListAdapter<InstructionItem, RecipeInstructionsAdapter.RecipeInstructionsViewHolder>(RecipeInstructionsDiffCallback()) {

    class RecipeInstructionsViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){

        fun bind(holder:RecipeInstructionsViewHolder,item:InstructionItem){

            var stepView:TextView = holder.itemView.findViewById(R.id.instructionStep)
            var descriptionView: TextView = holder.itemView.findViewById(R.id.instructionDescription)

            var stepString = "Step " + item.step.toString()
            var stepSpannable = SpannableString(stepString)
            stepView.text = stepSpannable.toString()
            descriptionView.text = item.description.toString()

        }

        companion object{

            fun from(parent:ViewGroup):RecipeInstructionsViewHolder{
                var view:View = LayoutInflater.from(parent.context).inflate(
                    R.layout.instruction_list_item,
                    parent,
                    false
                )

                return RecipeInstructionsViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecipeInstructionsViewHolder, position: Int) {
        var item = getItem(position)
        return holder.bind(holder,item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeInstructionsViewHolder {
        return RecipeInstructionsViewHolder.from(parent)
    }

    class OnClickListener(val clickListener:(instructionItem:InstructionItem)->Unit){
        fun onClick(instructionItem:InstructionItem) = clickListener(instructionItem)
    }
}

/**
 * @class RecipeInstructionDiffCallBack
 * @description Provides an implementation of DiffUtill which performs diffing
 * for individual list items, this ensures that individual list items are updated when they change
 * and that only those that change are updated.
 */
class RecipeInstructionsDiffCallback: DiffUtil.ItemCallback<InstructionItem>(){

    /**
     * @method: areItemsTheSame
     * @description compares items to see if they are the same type
     * @param {InstructionItem} oldItem
     * @param {InstructionItem} newItem
     * @return {Boolean} true if the items are the same type
     */
    override fun areItemsTheSame(oldItem: InstructionItem, newItem: InstructionItem): Boolean {
        return oldItem == newItem
    }

    /**
     * @method: areContentsTheSame
     * @description compares contents of items to see if they are the same.
     * @param {InstructionItem} oldItem
     * @param {InstructionItem} newItem
     * @return {Boolean} true if the items are the same
     */
    override fun areContentsTheSame(oldItem: InstructionItem, newItem: InstructionItem): Boolean {

        //TODO: FIX are contentTheSame
        //Currently it always returns true because oldItem gets updated before it is compared to newItem
        return false

    }
}