package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @class RecipeItem
 * @description Domain model for the recipe items
 * @param {String} title - the recipe title
 * @param {String} description - the recipe overview or summary
 * @param {String} likes - the aggregate number of likes the recipe has
 * @param {Boolean} glutenFree - true when recipe is gluten free
 * @param {Boolean} dairyFree - true when recipe is dairy free.
 * @param {Boolean} vegan - true when recipe is vegan
 * @param {Boolean} healthy - true when recipe is healthy
 * @param {Boolean} vegetarian - true when the recipe is vegetarian
 * @param {Boolean} fodmap - true when the recipe is fodmap
 * @param {Int} time - the preparation time in minutes
 * @param {String} sourceName - the source from where the recipe has been retrieved
 * @param {String} image - the image url as a string.
 */
@Parcelize
data class RecipeItem(
    var recipeId:Long,
    var freezerItem:String,
    var title:String?,
    var description:String?,
    var likes:Int,
    var glutenFree:Boolean,
    var dairyFree: Boolean,
    var vegan:Boolean,
    var healthy:Boolean,
    var vegetarian:Boolean,
    var fodmap:Boolean,
    var time:Int,
    var sourceName:String,
    var image:String
) : Parcelable {

    fun getPreference(preference:String):Boolean{

        return when(preference){
            "vegan" -> vegan
            "gluten free" -> glutenFree
            "dairy free" -> dairyFree
            "healthy" -> healthy
            "vegetarian" -> vegetarian
            "fodmap" -> fodmap
            else -> false
        }

    }
}



