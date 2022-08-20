package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FreezerItem(
    //val id:String,
    val name:String,
    val quantity:String,
    val unit:String,
    //val minimum:String,
    //val date:String,
): Parcelable
