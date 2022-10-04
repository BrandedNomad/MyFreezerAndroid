package com.myfreezer.app.ui.recipes

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.myfreezer.app.R
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.shared.utils.Utils
import com.myfreezer.app.ui.freezer.FreezerAdapter
import org.w3c.dom.Text

/**
 * @class RecipesAdapter
 * @description: The adapter for the recipe item recycler view
 * @param {OnClickListener} onClickListener - The clickListener that will be attached to every instance of the viewHolder
 */
class RecipesAdapter(val onClickListener:RecipesAdapter.OnClickListener): ListAdapter<RecipeItem,RecipesAdapter.RecipesViewHolder>(RecipesDiffCallback()) {

    /**
     * @class RecipesViewHolder
     * @description: The view holder for recipe_list_item
     * @param {View} viewItem - The layout or view of the list item
     */
    class RecipesViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){

        var textViewRecipeTitle = viewItem.findViewById<TextView>(R.id.recipe_card_title)
        var textViewSource = viewItem.findViewById<TextView>(R.id.recipe_card_body)
        var imageViewRecipeImage = viewItem.findViewById<ImageView>(R.id.recipe_card_image)
        var textViewRecipeLike = viewItem.findViewById<TextView>(R.id.recipe_card_icon_like)
        var textViewRecipeTime = viewItem.findViewById<TextView>(R.id.recipe_card_icon_time)
        var textViewRecipePreference = viewItem.findViewById<TextView>(R.id.recipe_card_preference_text)
        var imageViewRecipePreference = viewItem.findViewById<ImageView>(R.id.recipe_card_preference_icon)

        /**
         * @method bind
         * @description binds RecipesItem content to individual recipe_list_item
         * @param {RecipesViewHolder} holder: The view holder that holds the recipe_list_item
         * @param {RecipeItem} item: The content to be bound
         */
        fun bind(holder: RecipesAdapter.RecipesViewHolder, item:RecipeItem){

            //Create and style source string
            var tempString = "Source: " + item.sourceName
            var spanString = SpannableString(tempString)
            spanString.setSpan(StyleSpan(Typeface.BOLD),0,8,0)
            spanString.setSpan(StyleSpan(Typeface.ITALIC),8, spanString.length,0)
            spanString.setSpan(ForegroundColorSpan(textViewRecipePreference.getContext().getColor(R.color.primary)),8,spanString.length,0)
            var sourceString = spanString


            //Buffer and load image
            var requestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.fake_recipe)
                .error(R.drawable.fake_recipe)

            Glide.with(imageViewRecipeImage.getContext()).load(item.image).apply(requestOptions).into(holder.imageViewRecipeImage)

            //Bind values
            holder.textViewRecipeTitle.text = item.title
            holder.textViewSource.text = sourceString
            holder.textViewRecipeLike.text = item.likes.toString()
            holder.textViewRecipeTime.text = item.time.toString()

            //if preferences is false
            if(item.vegan == false){
                //Then set the icon and text to grey
                holder.imageViewRecipePreference.setColorFilter(imageViewRecipePreference.getContext().getColor(R.color.grey_five))
                holder.textViewRecipePreference.setTextColor(textViewRecipePreference.getContext().getColor(R.color.grey_five))
            }
        }

        companion object{
            /**
             * @method from
             * @description: Inflates the recipe_list_item view
             * @param {ViewGroup} parent: The parent context
             * @return {RecipesViewHolder} recipeViewHolder
             */
            fun from(parent: ViewGroup): RecipesAdapter.RecipesViewHolder {
                //inflate view
                val view:View = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.recipe_list_item,
                        parent,
                        false
                    )

                return RecipesViewHolder(view)
            }
        }
    }

    /**
     * @method onCreateViewHolder
     * @description lifecycle method that inflates the view on create.
     * @param {ViewGroup} parent: The parent view for context
     * @param {Int} viewType: The type of view as an integer
     * @return RecipesViewHolder - inflated view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    /**
     * @method onBindViewHolder
     * @description lifecycle method that handles the binding of inflated view
     * @param {RecipesViewHolder} holder: The inflated view
     * @param {Int} position: the item position in list
     */
    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        var item = getItem(position)
        holder.bind(holder,item)

        //Set onClickListener on each recipe
        holder.itemView.setOnClickListener{

            //When clicked navigates to recipe detail view
            onClickListener.onClick(item)
        }
    }

    /**
     * @class OnClickListener
     * @description Provides an instatiation of onClick to pass click listener to individual list items
     */
    class OnClickListener(val clickListener:(recipeItem: RecipeItem)-> Unit){
        fun onClick(recipeItem: RecipeItem) =  clickListener(recipeItem)

    }

}

/**
 * @class RecipesDiffCallBack
 * @description Provides an implementation of DiffUtill which performs diffing
 * for individual list items, this ensures that individual list items are updated when they change
 * and that only those that change are updated.
 */
class RecipesDiffCallback():DiffUtil.ItemCallback<RecipeItem>(){
    /**
     * @method: areItemsTheSame
     * @description compares items to see if they are the same type
     * @param {FreezerItem} oldItem
     * @param {FreezerItem} newItem
     * @return {Boolean} true if the items are the same type
     */
    override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem == newItem
    }

    /**
     * @method: areContentsTheSame
     * @description compares contents of items to see if they are the same.
     * @param {FreezerItem} oldItem
     * @param {FreezerItem} newItem
     * @return {Boolean} true if the items are the same
     */
    override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.title == newItem.title
    }


}
