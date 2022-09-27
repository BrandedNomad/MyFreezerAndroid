package com.myfreezer.app.repository.remote.interfaces

import com.myfreezer.app.BuildConfig
import com.myfreezer.app.repository.remote.responseclasses.GetRecipesResponse
import com.myfreezer.app.shared.constants.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RecipeService {



    @Headers(
        "Connection: keep-alive",
        "X-RapidAPI-Key: ${BuildConfig.SPOON_API_KEY}",
        "X-RapidAPI-Host: ${Constants.API_HOST}"
    )
    @GET("recipes/complexSearch")
    fun getRecipes(
        @Query("query") query: String,
        @Query("instructionsRequired") instructions: Boolean = true,
        @Query("fillIngredients") ingredients: Boolean = true,
        @Query("addRecipeInformation") information: Boolean = true
    ): Call<GetRecipesResponse>
}