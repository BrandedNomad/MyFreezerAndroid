package com.myfreezer.app.ui.recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myfreezer.app.R
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.ui.freezer.FreezerAdapter
import org.w3c.dom.Text

class RecipesAdapter(onClickListener:RecipesAdapter.OnClickListener): ListAdapter<RecipeItem,RecipesAdapter.RecipesViewHolder>(RecipesDiffCallback()) {

    class RecipesViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){

        var textViewRecipeTitle = viewItem.findViewById<TextView>(R.id.recipe_card_title)
        var textViewRecipeDescription = viewItem.findViewById<TextView>(R.id.recipe_card_body)
        var imageViewRecipeImage = viewItem.findViewById<ImageView>(R.id.recipe_card_image)
        var textViewRecipeLike = viewItem.findViewById<TextView>(R.id.recipe_card_icon_like)
        var textViewRecipeTime = viewItem.findViewById<TextView>(R.id.recipe_card_icon_time)
        var textViewRecipeVeg = viewItem.findViewById<TextView>(R.id.recipe_card_icon_veg)

        /**
         * @method bind
         * @description binds freezerItem content to individual freezer_list_item
         * @param {FreezerViewHolder} holder: The view holder that holds the freezer_list_item
         * @param {FreezerItem} item: The content to be bound
         */
        fun bind(holder: RecipesAdapter.RecipesViewHolder, item:RecipeItem){


            holder.textViewRecipeTitle.text = item.title
            holder.textViewRecipeDescription.text = "Authentic Italian tomato based pasta with a bacon bits and rich sauce"
            //holder.imageViewRecipeImage = item.image
            //holder.textViewRecipeLike =
            //holder.textViewRecipeTime =
            //holder.textViewRecipeVeg =
        }


        companion object{
            /**
             * @method from
             * @description: Inflates the freezer_list_item view
             * @param {ViewGroup} parent: The parent context
             * @return {Object} FreezerViewHolder
             */
            fun from(parent: ViewGroup): RecipesAdapter.RecipesViewHolder {
                val view:View = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recipe_list_item,
                        parent,
                        false
                    )

                return RecipesAdapter.RecipesViewHolder(view)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        var item = getItem(position)
        holder.bind(holder,item)
    }

    /**
     * @class OnClickListener
     * @description Provides an instatiation of onClick to pass click listener to individual list items
     */
    class OnClickListener(val clickListener:(recipeItem: RecipeItem)-> Unit){
        fun onClick(recipeItem: RecipeItem) =  clickListener(recipeItem)

    }

}

class RecipesDiffCallback():DiffUtil.ItemCallback<RecipeItem>(){
    override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.title == newItem.title
    }


}
