package com.myfreezer.app.repository.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myfreezer.app.models.IngredientItem

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DatabaseRecipe::class,
            parentColumns = arrayOf("recipe_ID"),
            childColumns = arrayOf("recipe_ID"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class DatabaseIngredientItem(
    @PrimaryKey(autoGenerate = true)
    var ingredient_ID:Long?,
    var recipe_ID:Long?,
    var name:String?="",
    var amount:Double?=0.0,
    var unit:String?="",
    var image:String?=""
){
    constructor(
        recipe_ID:Long?,
        name:String?="",
        amount:Double?=0.0,
        unit:String?="",
        image:String?=""
    ):this(
        null,
        recipe_ID,
        name,
        amount,
        unit,
        image,
    )
}

fun List<DatabaseIngredientItem>.asDomainModel():List<IngredientItem>{

    return map{
        IngredientItem(
            name = it.name,
            amount = it.amount,
            unit = it.unit,
            image = it.image
        )
    }
}