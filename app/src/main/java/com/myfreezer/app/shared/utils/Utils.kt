package com.myfreezer.app.shared.utils

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object{
        fun dateToString(date:Date):String {
            val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd");
            val dateString:String = sdf.format(Date());
            return dateString
        }

        fun stringToDate(dateString:String):Date? {
            val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = sdf.parse(dateString)
            return date

        }
    }



}