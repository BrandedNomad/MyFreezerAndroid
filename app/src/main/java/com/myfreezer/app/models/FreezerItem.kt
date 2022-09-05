package com.myfreezer.app.models

import android.os.Parcelable
import com.myfreezer.app.repository.local.DatabaseFreezerItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FreezerItem(
    //val id:String,
    var name:String,
    var quantity:Int,
    var unit:String,
    var minimum:Int,
    //val date:String,
): Parcelable


/**
 * @description: returns a list of freezerItems to be used for display
 */
//fun FreezerItem.asDataModel():DatabaseFreezerItem{
//
//
//    return DatabaseFreezerItem(
//            name = it.name,
//            quantity = it.quantity,
//            unit = it.unit,
//            minimum = it.minimum
//        )
//
//}
