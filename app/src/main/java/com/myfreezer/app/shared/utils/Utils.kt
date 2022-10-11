package com.myfreezer.app.shared.utils


import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

/**
 * @class Utils
 * @description: The utils class contain various helper functions that are used across the application
 */
class Utils {

    companion object{

        /**
         * @method dateToString
         * @description formats a date object into a string
         * @param {Date} date - the date to be formatted
         * @return {String} dateString - the formatted string.
         */

        fun dateToString(date:Date):String {
            val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd");
            val dateString:String = sdf.format(Date());
            return dateString
        }


        /**
         * @method stringToDate
         * @description converts a string into a date object
         * @param {String} dateString - the string to be converted
         * @retun {Date?} the date object
         */

        fun stringToDate(dateString:String):Date? {
            val sdf:SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = sdf.parse(dateString)
            return date

        }


        /**
         * @method ShortedDescription
         * @description: used to shorten title and overview strings in previews so that they don't break the layout
         * @param {String} description - the string to be shortened
         * @return {String} completeShortDescription - the shortened description
         */
        fun shortenDescription(description:String):String {
            var shortDescription = description.slice(0..65)
            var completeShortDescription = shortDescription.plus("...")
            return completeShortDescription

        }

        /**
         * @method htmlToText
         * @description used to format html to strings, or to strip a string of its html tags
         * @param {String} html - the string which contains the html tags
         * @return {String} - the formatted string.
         */
        fun htmlToText(html:String):String {
                return Jsoup.parse(html).text()
        }

    }



}