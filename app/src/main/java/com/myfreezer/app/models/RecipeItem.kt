package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeItem(
    var title:String?,
    var description:String?,
    var likes:Int,
    var glutenFree:Boolean,
    var dairyFree: Boolean,
    var vegan:Boolean,
    var healthy:Boolean,
    var vegetarian:Boolean,
    var fodmap:Boolean,
    var time:Int,
    var sourceName:String,
    var image:String
) : Parcelable
