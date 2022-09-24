package com.myfreezer.app.repository.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.shared.utils.Utils


@Entity
data class DatabaseRecipe(
    @PrimaryKey(autoGenerate = true)
    var recipe_ID:Int? = 0,
    val freezerItem_ID:String,
    val title:String,
    val description:String,
    val likes: Int = 0,
    val glutenFree:Boolean = false,
    val dairyFree:Boolean = false,
    val vegan: Boolean = false,
    val healthy: Boolean = false,
    val vegetarian:Boolean = false,
    val fodmap: Boolean = false,
    val time: Int = 0,
    val sourceName:String,
    val image:String

){
    constructor(
        freezerItem_ID:String,
        title:String,
        description:String,
        likes:Int,
        glutenFree:Boolean,
        dairyFree:Boolean,
        vegan:Boolean,
        healthy:Boolean,
        vegetarian:Boolean,
        fodmap:Boolean,
        time:Int,
        sourceName:String,
        image:String
    ):this(
        null,
        freezerItem_ID,
        title,
        description,
        likes,
        glutenFree,
        dairyFree,
        vegan,
        healthy,
        vegetarian,
        fodmap,
        time,
        sourceName,
        image)
}

/**
 * @description: returns a list of freezerItems to be used for display
 */
fun List<DatabaseRecipe>.asDomainModel():List<RecipeItem>{

    return map{
        RecipeItem(
            title = it.title,
            description = it.description,
            likes = it.likes,
            glutenFree = it.glutenFree,
            dairyFree = it.dairyFree,
            vegan = it.vegan,
            healthy = it.healthy,
            vegetarian = it.vegetarian,
            fodmap = it.fodmap,
            time  = it.time,
            sourceName = it.sourceName,
            image = it.image
        )
    }
}