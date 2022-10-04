package com.myfreezer.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InstructionItem(
    var step:Int,
    var description:String?=""
) : Parcelable