package com.myfreezer.app.repository.local.entities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.shared.utils.Utils
import java.util.*

@Entity
data class DatabaseFreezerItem(
    @PrimaryKey
    val name:String,
    val quantity:Int,
    val unit:String,
    val minimum:Int,
    val dateAddedString: String
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
            minimum = it.minimum,
            dateAddedString = it.dateAddedString,
            dateAdded = Utils.stringToDate(it.dateAddedString)!!
        )
    }
}





