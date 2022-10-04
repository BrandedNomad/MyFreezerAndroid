package com.myfreezer.app.repository.local.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.shared.utils.Utils

/**
 * @entity DataBaseFreezerItem
 * @description: DataBaseFreezerItem is the entity model for items in the freezer
 * @param {String} name - the item name.
 * @param {Int} quantity - the item quantity
 * @param {String} unit - the unit of measurement kg, g, pcs, l, ml, etc.
 * @param {Int} minimum - the minimum level that item should be maintained at. Used to send notifications when Item drops below this level
 * @param {String} dateAddedString: The date when item was added as a String.
 */
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
 * @method: asDomainModel
 * @description: Converts the Entity model to a Domain model
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





