package com.myfreezer.app.models

import android.os.Parcelable
import com.myfreezer.app.repository.local.DatabaseFreezerItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FreezerItem(
    //val id:String,
    val name:String,
    val quantity:Int,
    val unit:String,
    val minimum:Int,
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
