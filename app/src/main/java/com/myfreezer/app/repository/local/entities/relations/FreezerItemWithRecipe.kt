package com.myfreezer.app.repository.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe

/**
 * @relation RecipeAndFreezerItem
 * @description Describes the relationship between Recipe and FreezerItem
 * @param {DatabaseRecipe} databaseRecipe - the recipe entity
 * @param {DatabaseFreezerItem} databaseFreezerItem - the freezerItem entity
 */
data class FreezerItemWithRecipe(
    @Embedded val freezerItem: DatabaseFreezerItem,
    @Relation(
        parentColumn = "name",
        entityColumn = "freezerItem_ID"
    )
    val recipe: List<DatabaseRecipe>
)