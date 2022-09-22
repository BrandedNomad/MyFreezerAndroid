package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class FreezerItem(
    //val id:String,
    var name:String,
    var quantity:Int,
    var unit:String,
    var minimum:Int,
    var dateAddedString: String,
    var dateAdded:Date
): Parcelable



