package com.myfreezer.app.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myfreezer.app.models.FreezerItem

@Entity
data class DatabaseFreezerItem(
    @PrimaryKey
    val name:String,
    val quantity:Int,
    val unit:String,
    val minimum:Int
)

/**
 * @description: returns a list of freezerItems to be used for display
 */
fun List<DatabaseFreezerItem>.asDomainModel():List<FreezerItem>{
    return map{
        FreezerItem(
            name = it.name,
            quantity = it.quantity,
            unit = it.unit,
            minimum = it.minimum
        )
    }
}