package com.aguilar.chefai.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbApi {
    // 1. Para la lista inicial
    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): MealResponse

    // 2. NUEVO: Para la lupa de búsqueda
    @GET("search.php")
    suspend fun searchRecipesByName(@Query("s") query: String): MealResponse

    // 3. NUEVO: Para ver los ingredientes y preparación de una receta
    @GET("lookup.php")
    suspend fun getRecipeDetails(@Query("i") id: String): MealDetailResponse
}

data class MealResponse(val meals: List<MealDto>?)
data class MealDto(val idMeal: String, val strMeal: String, val strMealThumb: String)

data class MealDetailResponse(val meals: List<MealDetailDto>?)
data class MealDetailDto(
    val idMeal: String,
    val strMeal: String,
    val strInstructions: String,
    val strMealThumb: String,
    // La API de TheMealDB devuelve los ingredientes uno por uno del 1 al 15:
    val strIngredient1: String?, val strIngredient2: String?, val strIngredient3: String?,
    val strIngredient4: String?, val strIngredient5: String?, val strIngredient6: String?,
    val strIngredient7: String?, val strIngredient8: String?, val strIngredient9: String?,
    val strIngredient10: String?, val strIngredient11: String?, val strIngredient12: String?,
    val strIngredient13: String?, val strIngredient14: String?, val strIngredient15: String?
)