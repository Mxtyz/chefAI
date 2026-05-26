package com.aguilar.chefai.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Objeto envoltorio de la respuesta de TheMealDB.
 */
data class RecipeResponse(
    @SerializedName("meals")
    val meals: List<RecipeDto>?
)