package com.myfreezer.app.ui.main

import com.myfreezer.app.models.RecipeItem

interface Communicator {
    fun transferData(data: RecipeItem)
}