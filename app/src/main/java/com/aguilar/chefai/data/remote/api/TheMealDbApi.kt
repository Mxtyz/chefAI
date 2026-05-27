package com.aguilar.chefai.data.remote.api

import com.aguilar.chefai.data.remote.dto.MealDetailResponse
import com.aguilar.chefai.data.remote.dto.MealResponse
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

