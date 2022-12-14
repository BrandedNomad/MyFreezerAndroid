package com.myfreezer.app.repository.remote.responseclasses

import com.myfreezer.app.models.IngredientItem
import com.myfreezer.app.models.InstructionItem
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.models.RecipeResponse
import com.myfreezer.app.repository.local.entities.DatabaseIngredientItem
import com.myfreezer.app.repository.local.entities.DatabaseRecipe
import com.myfreezer.app.shared.utils.Utils
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class GetRecipesResponse(
    @Json(name = "results")
    val results: List<Result> = listOf(),
    @Json(name = "offset")
    val offset: Int = 0,
    @Json(name = "number")
    val number: Int = 0,
    @Json(name = "totalResults")
    val totalResults: Int = 0
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "vegetarian")
        val vegetarian: Boolean = false,
        @Json(name = "vegan")
        val vegan: Boolean = false,
        @Json(name = "glutenFree")
        val glutenFree: Boolean = false,
        @Json(name = "dairyFree")
        val dairyFree: Boolean = false,
        @Json(name = "veryHealthy")
        val veryHealthy: Boolean = false,
        @Json(name = "cheap")
        val cheap: Boolean = false,
        @Json(name = "veryPopular")
        val veryPopular: Boolean = false,
        @Json(name = "sustainable")
        val sustainable: Boolean = false,
        @Json(name = "lowFodmap")
        val lowFodmap: Boolean = false,
        @Json(name = "weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int = 0,
        @Json(name = "gaps")
        val gaps: String? = null,
        @Json(name = "preparationMinutes")
        val preparationMinutes: Int = 0,
        @Json(name = "cookingMinutes")
        val cookingMinutes: Int = 0,
        @Json(name = "aggregateLikes")
        val aggregateLikes: Int = 0,
        @Json(name = "healthScore")
        val healthScore: Int = 0,
        @Json(name = "creditsText")
        val creditsText: String? = null,
        @Json(name = "sourceName")
        val sourceName: String? = null,
        @Json(name = "pricePerServing")
        val pricePerServing: Double = 0.0,
        @Json(name = "extendedIngredients")
        val extendedIngredients: List<ExtendedIngredient> = listOf(),
        @Json(name = "id")
        val id: Int = 0,
        @Json(name = "title")
        val title: String? = null,
        @Json(name = "readyInMinutes")
        val readyInMinutes: Int = 0,
        @Json(name = "servings")
        val servings: Int = 0,
        @Json(name = "sourceUrl")
        val sourceUrl: String? = null,
        @Json(name = "image")
        val image: String? = null,
        @Json(name = "imageType")
        val imageType: String? = null,
        @Json(name = "summary")
        val summary: String? = null,
        @Json(name = "cuisines")
        val cuisines: List<String> = listOf(),
        @Json(name = "dishTypes")
        val dishTypes: List<String> = listOf(),
        @Json(name = "diets")
        val diets: List<String> = listOf(),
        @Json(name = "occasions")
        val occasions: List<Any> = listOf(),
        @Json(name = "analyzedInstructions")
        val analyzedInstructions: List<AnalyzedInstruction> = listOf(),
        @Json(name = "usedIngredientCount")
        val usedIngredientCount: Int = 0,
        @Json(name = "missedIngredientCount")
        val missedIngredientCount: Int = 0,
        @Json(name = "missedIngredients")
        val missedIngredients: List<MissedIngredient> = listOf(),
        @Json(name = "likes")
        val likes: Int = 0,
        @Json(name = "usedIngredients")
        val usedIngredients: List<Any> = listOf(),
        @Json(name = "unusedIngredients")
        val unusedIngredients: List<Any> = listOf()
    ) {
        @JsonClass(generateAdapter = true)
        data class ExtendedIngredient(
            @Json(name = "id")
            val id: Int = 0,
            @Json(name = "aisle")
            val aisle: String? = null,
            @Json(name = "image")
            val image: String? = null,
            @Json(name = "consistency")
            val consistency: String? = null,
            @Json(name = "name")
            val name: String? = null,
            @Json(name = "nameClean")
            val nameClean: String? = null,
            @Json(name = "original")
            val original: String? = null,
            @Json(name = "originalName")
            val originalName: String? = null,
            @Json(name = "amount")
            val amount: Double = 0.0,
            @Json(name = "unit")
            val unit: String? = null,
            @Json(name = "meta")
            val meta: List<String> = listOf(),
            @Json(name = "measures")
            val measures: Measures = Measures()
        ) {
            @JsonClass(generateAdapter = true)
            data class Measures(
                @Json(name = "us")
                val us: Us = Us(),
                @Json(name = "metric")
                val metric: Metric = Metric()
            ) {
                @JsonClass(generateAdapter = true)
                data class Us(
                    @Json(name = "amount")
                    val amount: Double = 0.0,
                    @Json(name = "unitShort")
                    val unitShort: String? = null,
                    @Json(name = "unitLong")
                    val unitLong: String? = null
                )

                @JsonClass(generateAdapter = true)
                data class Metric(
                    @Json(name = "amount")
                    val amount: Double = 0.0,
                    @Json(name = "unitShort")
                    val unitShort: String? = null,
                    @Json(name = "unitLong")
                    val unitLong: String? = null
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class AnalyzedInstruction(
            @Json(name = "name")
            val name: String? = null,
            @Json(name = "steps")
            val steps: List<Step> = listOf()
        ) {
            @JsonClass(generateAdapter = true)
            data class Step(
                @Json(name = "number")
                val number: Int = 0,
                @Json(name = "step")
                val step: String = "",
                @Json(name = "ingredients")
                val ingredients: List<Ingredient> = listOf(),
                @Json(name = "equipment")
                val equipment: List<Equipment> = listOf(),
                @Json(name = "length")
                val length: Length? = Length()
            ) {
                @JsonClass(generateAdapter = true)
                data class Ingredient(
                    @Json(name = "id")
                    val id: Int = 0,
                    @Json(name = "name")
                    val name: String = "",
                    @Json(name = "localizedName")
                    val localizedName: String = "",
                    @Json(name = "image")
                    val image: String = ""
                )

                @JsonClass(generateAdapter = true)
                data class Equipment(
                    @Json(name = "id")
                    val id: Int = 0,
                    @Json(name = "name")
                    val name: String = "",
                    @Json(name = "localizedName")
                    val localizedName: String = "",
                    @Json(name = "image")
                    val image: String = ""
                )

                @JsonClass(generateAdapter = true)
                data class Length(
                    @Json(name = "number")
                    val number: Int = 0,
                    @Json(name = "unit")
                    val unit: String = ""
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class MissedIngredient(
            @Json(name = "id")
            val id: Int = 0,
            @Json(name = "amount")
            val amount: Double = 0.0,
            @Json(name = "unit")
            val unit: String = "",
            @Json(name = "unitLong")
            val unitLong: String = "",
            @Json(name = "unitShort")
            val unitShort: String = "",
            @Json(name = "aisle")
            val aisle: String = "",
            @Json(name = "name")
            val name: String = "",
            @Json(name = "original")
            val original: String = "",
            @Json(name = "originalName")
            val originalName: String = "",
            @Json(name = "meta")
            val meta: List<String> = listOf(),
            @Json(name = "image")
            val image: String = "",
            @Json(name = "extendedName")
            val extendedName: String? = ""
        )
    }
}

fun GetRecipesResponse.asDomainModel(itemName:String):List<RecipeResponse>{
    var listToProcess: ArrayList<DatabaseRecipe> = arrayListOf()
    var ingredientList = mutableListOf<IngredientItem>()
    var listOfIngredientLists = mutableListOf<MutableList<IngredientItem>>()

    for(item in results.iterator()){
        for(ingredient in item.extendedIngredients.iterator()){
            var ingredientItem = IngredientItem(
                ingredient.name,
                ingredient.amount,
                ingredient.unit,
                ingredient.image,
            )

            ingredientList.add(ingredientItem)

        }
        listOfIngredientLists.add(ingredientList)
        ingredientList.clear()
    }

    var recipe = results.map{
        RecipeResponse(
            itemName,
            it.title!!,
            Utils.htmlToText(it.summary!!),
            it.aggregateLikes,
            it.glutenFree,
            it.dairyFree,
            it.vegan,
            it.veryHealthy,
            it.vegetarian,
            it.lowFodmap,
            it.preparationMinutes,
            it.sourceName!!,
            it.image!!,
            it.extendedIngredients.map{
                IngredientItem(
                    it.name,
                    it.amount,
                    it.unit,
                    it.image
                )
            },
            it.analyzedInstructions[0].steps.map{
                InstructionItem(
                    it.number,
                    it.step
                )
            }

        )
    }

    return recipe

//    var array = results.map{
//        DatabaseRecipe(
//            itemName,
//            it.title!!,
//            Utils.htmlToText(it.summary!!),
//            it.aggregateLikes,
//            it.glutenFree,
//            it.dairyFree,
//            it.vegan,
//            it.veryHealthy,
//            it.vegetarian,
//            it.lowFodmap,
//            it.preparationMinutes,
//            it.sourceName!!,
//            it.image!!
//        )
//    }
//
//
//    return array.toTypedArray()

}