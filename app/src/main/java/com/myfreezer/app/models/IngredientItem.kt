package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientItem(
    var name:String? = "",
    var amount:Double? = 0.0,
    var unit:String? = "",
    var image:String? = ""
) : Parcelable