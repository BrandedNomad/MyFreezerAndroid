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
    val vegan: Boolean = false,
    val time: Int = 0,
    val sourceName:String,
    val image:String

){
    constructor(
        freezerItem_ID:String,
        title:String,
        description:String,
        likes:Int,
        vegan:Boolean,
        time:Int,
        sourceName:String,
        image:String
    ):this(
        null,
        freezerItem_ID,
        title,
        description,
        likes,
        vegan,
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
            vegan = it.vegan,
            time  = it.time,
            sourceName = it.sourceName,
            image = it.image
        )
    }
}