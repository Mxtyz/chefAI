package com.aguilar.chefai.data.remote.api

import com.aguilar.chefai.data.remote.dto.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definición de los endpoints de Retrofit.
 */
interface RecipeApiService {
    @GET("filter.php")
    suspend fun getRecipesByCategory(
        @Query("c") category: String
    ): RecipeResponse
}
