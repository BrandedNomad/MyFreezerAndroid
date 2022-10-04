package com.myfreezer.app.repository.remote.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.myfreezer.app.repository.remote.interfaces.RecipeService
import com.myfreezer.app.shared.constants.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


var moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var retrofit = Retrofit.Builder()
    .baseUrl(Constants.RECIPE_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

object Network {
    val recipesAPI: RecipeService = retrofit.create(RecipeService::class.java)
}