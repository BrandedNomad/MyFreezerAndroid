package com.myfreezer.app.repository.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myfreezer.app.models.RecipeItem


/**
 * @entity RecipeItem
 * @description Entity model for the recipe table
 * @param {Int} recipe_ID - the autogenerated PrimaryKey
 * @param {String} freezerItem_ID - the foreignKey that associates the recipe with a freezerItem
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
@Entity(
    foreignKeys = arrayOf(
        ForeignKey(entity = DatabaseFreezerItem::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("freezerItem_ID"),
        onDelete = ForeignKey.CASCADE) //supposed to delete all recipes
    )
)
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
        null, //set to null so that freezerItem_ID does not have to be supplied.
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
 * @method asDomainModel
 * @description: Converts Entity to DomainModel
 */
fun List<DatabaseRecipe>.asDomainModel():List<RecipeItem>{

    return map{
        RecipeItem(
            recipeId = it.recipe_ID!!.toLong(),

            freezerItem = it.freezerItem_ID,

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