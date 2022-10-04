package com.myfreezer.app.repository.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.myfreezer.app.repository.local.entities.DatabaseIngredientItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe



data class RecipeWithInstructionItem(
    @Embedded val databaseRecipe: DatabaseRecipe,
    @Relation(
        parentColumn = "recipe_ID",
        entityColumn = "recipe_ID"
    )
    val databaseInstructionItem: List<DatabaseIngredientItem>
)