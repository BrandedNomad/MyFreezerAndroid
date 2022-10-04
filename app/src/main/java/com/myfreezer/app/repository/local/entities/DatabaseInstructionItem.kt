package com.myfreezer.app.repository.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.myfreezer.app.models.InstructionItem

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


data class DatabaseInstructionItem(
    @PrimaryKey(autoGenerate = true)
    var instruction_ID:Long?,
    var recipe_ID:Long?,
    var step:Int?,
    var description:String?=""
){
    constructor(
        recipe_ID:Long?,
        step:Int?,
        description:String?=""
    ):this(
        null,
        recipe_ID,
        step,
        description
    )
}

fun List<DatabaseInstructionItem>.asDomainModel(): List<InstructionItem> {

    return map{
        InstructionItem(
            step = it.step!!,
            description = it.description
        )
    }
}