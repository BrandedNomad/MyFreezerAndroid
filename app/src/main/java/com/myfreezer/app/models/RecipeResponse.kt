package com.myfreezer.app.models

import android.util.Log
import com.myfreezer.app.repository.local.entities.DatabaseIngredientItem
import com.myfreezer.app.repository.local.entities.DatabaseInstructionItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe
import com.myfreezer.app.repository.remote.responseclasses.GetRecipesResponse
import java.lang.Exception

data class RecipeResponse(
    var itemName:String,
    var title:String,
    var summary:String,
    var aggregateLikes:Int,
    var glutenFree:Boolean,
    var dairyFree:Boolean,
    var vegan:Boolean,
    var veryHealthy:Boolean,
    var vegetarian:Boolean,
    var lowFodmap:Boolean,
    var preparationMinutes:Int,
    var sourceName:String,
    var image:String,
    var ingredients:List<IngredientItem>,
    var instructions:List<InstructionItem>
)

fun RecipeResponse.asDatabaseModelRecipe():DatabaseRecipe{

        return DatabaseRecipe(
            itemName,
            title,
            summary,
            aggregateLikes,
            glutenFree,
            dairyFree,
            vegan,
            veryHealthy,
            vegetarian,
            lowFodmap,
            preparationMinutes,
            sourceName,
            image
        )
}

fun RecipeResponse.asDatabaseModelIngredients(recipeID:Long):Array<DatabaseIngredientItem>{
    Log.e("Response",recipeID.toString())
    Log.e("Response-ingredient-list",ingredients.toString())
    lateinit var array:List<DatabaseIngredientItem>
    try{
        array = ingredients.map{

            DatabaseIngredientItem(
                recipeID,
                if(it.name.isNullOrBlank()) "no name" else it.name,
                if(it.amount!!.isNaN()) 0.0 else it.amount,
                if(it.unit.isNullOrBlank()) "no unit" else it.unit,
                if(it.image.isNullOrBlank()) "no image" else  it.image
            )

        }
    }catch (error:Exception){
        Log.e("ERROR-from-response",error.toString())
    }


    Log.e("Response - array", array.toString())

    return array.toTypedArray()
}

fun RecipeResponse.asDatabaseModelInstructions(recipeID:Long):Array<DatabaseInstructionItem>{
    lateinit var array:List<DatabaseInstructionItem>
    try{

        array = instructions.map{

            DatabaseInstructionItem(
                recipeID,
                if(it.step == null) -1 else it.step,
                if(it.description.isNullOrBlank()) "none" else it.description
            )
        }
    }catch(error:Exception){
        Log.e("ERROR-from-response2",error.toString())
    }

    return array.toTypedArray()
}