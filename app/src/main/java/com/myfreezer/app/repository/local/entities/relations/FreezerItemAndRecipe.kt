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
data class RecipeAndFreezerItem(
    @Embedded val databaseRecipe: DatabaseRecipe,
    @Relation(
        parentColumn = "FreezerItem_ID",
        entityColumn = "name"
    )
    val databaseFreezerItem: DatabaseFreezerItem
)