package com.myfreezer.app.repository.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.myfreezer.app.repository.local.entities.DatabaseFreezerItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe

data class RecipeAndFreezerItem(
    @Embedded val databaseRecipe: DatabaseRecipe,
    @Relation(
        parentColumn = "FreezerItem_ID",
        entityColumn = "name"
    )
    val databaseFreezerItem: DatabaseFreezerItem
)