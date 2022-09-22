package com.myfreezer.app.models

data class RecipeItem(
    var title:String?,
    var description:String?,
    var likes:Int,
    var vegan:Boolean,
    var time:Int,
    var sourceName:String,
    var image:String
)
