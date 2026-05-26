package com.aguilar.chefai.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbApi {
    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): MealResponse
}

// Estructura de los datos que vienen de internet
data class MealResponse(val meals: List<MealDto>?)
data class MealDto(val idMeal: String, val strMeal: String, val strMealThumb: String)