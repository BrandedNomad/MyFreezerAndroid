package com.myfreezer.app.models

import android.os.Parcelable
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
