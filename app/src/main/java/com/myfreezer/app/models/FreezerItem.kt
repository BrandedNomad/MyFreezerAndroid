package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * @class FreezerItem
 * @description: FreezerItem is the domain model for items in the freezer
 * @param {String} name - the item name.
 * @param {Int} quantity - the item quantity
 * @param {String} unit - the unit of measurement kg, g, pcs, l, ml, etc.
 * @param {Int} minimum - the minimum level that item should be maintained at. Used to send notifications when Item drops below this level
 * @param {String} dateAddedString: The date when item was added as a String.
 * @param {Date} dateAdded: The date when item was added as a dateObject, used for sorting
 */
@Parcelize
data class FreezerItem(
    var name:String,
    var quantity:Int,
    var unit:String,
    var minimum:Int,
    var dateAddedString: String,
    var dateAdded:Date
): Parcelable



