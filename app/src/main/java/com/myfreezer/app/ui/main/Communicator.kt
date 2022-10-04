package com.myfreezer.app.ui.main

import com.myfreezer.app.models.RecipeItem

/**
 * @interface: Communicator
 * @description: provide declarations of an abstract method for data transfer between fragments. This method is implemented in the MainActivity
 * but are accessed and called from the fragments.
 */
interface Communicator {
    /**
     * @declaration: transferData
     * @description: An declaration of an abstract method to be used for transfering data between fragments
     * @param {RecipeItem} data - the RecipeItem to be passed to the details view
     */
    fun transferData(data: RecipeItem)
}