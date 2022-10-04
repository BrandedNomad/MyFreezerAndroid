package com.myfreezer.app.repository.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.myfreezer.app.repository.local.entities.DatabaseIngredientItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe


data class RecipeWithIngredientItem(
    @Embedded val databaseRecipe: DatabaseRecipe,
    @Relation(
        parentColumn = "recipe_ID",
        entityColumn = "recipe_ID"
    )
    val databaseIngredientItem: List<DatabaseIngredientItem>
)